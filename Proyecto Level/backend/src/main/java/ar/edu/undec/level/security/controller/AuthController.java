package ar.edu.undec.level.security.controller;


import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.security.dto.*;
import ar.edu.undec.level.security.entity.*;
import ar.edu.undec.level.security.jwt.JwtProvider;
import ar.edu.undec.level.security.service.RolService;
import ar.edu.undec.level.security.service.UsuarioService;
import ar.edu.undec.level.service.EmailService;
import ar.edu.undec.level.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    EmailService emailService;

    @Autowired
    private PedidosService pedidosService;

    //Esto espera un json, lo convierte en una clase java tipo Nuevo Usuario,
    //para validarlo BindingResult
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Optional<Usuario> usuarioEncontrado = usuarioService.findByEmail(nuevoUsuario.getEmail());
        if(usuarioEncontrado.isPresent()){
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getApellido(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));

        Set<Rol> rolesTransformados = nuevoUsuario.getRoles().stream()
                .map(r -> new Rol(r.getId(), r.getRolNombre()))
                .collect(Collectors.toSet());

        usuario.setRoles(rolesTransformados);
        Usuario usuarioCreado = usuarioService.save(usuario);
        crearHistorialCuandoSeCreaUsuario(usuarioCreado);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

/*
* va a devolver un token, usando el nombre de usuario y contraseña
* */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<Usuario> usuarioEncontrado = usuarioService.getByNombreUsuario(loginUsuario.getNombreUsuario());
        if(!usuarioEncontrado.isPresent()) {
            return new ResponseEntity(new Mensaje("Usuario no existe"), HttpStatus.NOT_FOUND);
        }

        if(!usuarioEncontrado.get().getActivo()) return new ResponseEntity(new Mensaje("El usuario '"+ usuarioEncontrado.get().getNombreUsuario()+ "' está deshabilitado"), HttpStatus.CONFLICT);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Response> getUsuarios() {
        Response response = new Response();
        response.setData(usuarioService.findAll());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Response> getRoles() {
        Response response = new Response();
        response.setData(rolService.getRoles());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/buscar-por-usuario/{nombreUsuario}")
    public ResponseEntity<Response> buscarPorUsuario(@PathVariable String nombreUsuario){
        Response response = new Response();
        response.setData(usuarioService.getByNombreUsuario(nombreUsuario).get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) {

        Optional<Usuario> usuario = usuarioService.findByEmail(emailDto.getMailTo());

        if(!usuario.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No se encontró el usuario: " + emailDto.getMailTo()), HttpStatus.NOT_FOUND);
        }

        Usuario usuarioEncontrado = usuario.get();

        emailDto.setMailFrom("levelsystems23@gmail.com");
        emailDto.setMailTo(usuarioEncontrado.getEmail());
        emailDto.setSubject("Restablecer contraseña");
        emailDto.setUsername(usuarioEncontrado.getNombreUsuario());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailDto.setToken(tokenPassword);
        emailService.sendEmail(emailDto);
        usuarioEncontrado.setTokenPassword(tokenPassword);
        usuarioService.save(usuarioEncontrado);

        return new ResponseEntity<>(new Mensaje("Se envió un correo para restablecer la contraseña."), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        if(!changePassword.getPassword().equals(changePassword.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuario = usuarioService.findByTokenPassword(changePassword.getTokenPassword());
        if(!usuario.isPresent())
            return new ResponseEntity(new Mensaje("no existe usuario con esas credenciales"), HttpStatus.BAD_REQUEST);

        Usuario usuarioEncontrado = usuario.get();

        String newPassword = passwordEncoder.encode(changePassword.getPassword());
        usuarioEncontrado.setPassword(newPassword);
        usuarioEncontrado.setTokenPassword(null);
        usuarioService.save(usuarioEncontrado);

        return new ResponseEntity<>(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }

    @PutMapping("/actualizar-usuario")
    public ResponseEntity<?> actualizarUsuario(@RequestBody NuevoUsuario usuarioFront){
//        Optional<Usuario> usuarioEncontrado = usuarioService.getByNombreUsuario(usuarioFront.getNombreUsuario());
        Optional<Usuario> usuarioEncontrado = usuarioService.buscarUsuarioPorId(usuarioFront.getId());



        if(!usuarioEncontrado.isPresent()){
            return new ResponseEntity<>(new Mensaje("No existe usuario con ese ID"), HttpStatus.NOT_FOUND);
        }

        Usuario usuarioOk = usuarioEncontrado.get();

        if(!usuarioOk.getActivo().equals(usuarioFront.getActivo())) {
            String detalle = "Se cambió el estado de "+estadoToString(usuarioOk.getActivo())+" a " + estadoToString(usuarioFront.getActivo())+ "." ;
            nuevoHistorialUsuario(detalle, usuarioOk);
            usuarioOk.setActivo(usuarioFront.getActivo());
            usuarioService.save(usuarioOk);
            return new ResponseEntity<>(new Mensaje("Usuario actualizado"), HttpStatus.CREATED);
        }

        if(usuarioFront.getRoles().size() != usuarioOk.getRoles().size()) {
            List<String> rolesUsuarioFront = cambiarRolesToString(usuarioFront.getRoles());
            agregarHistorialRolesUsuario(usuarioOk, rolesUsuarioFront);
        }

        agregarHistorialAtributosUsuario(usuarioEncontrado.get(), usuarioFront);

        usuarioOk.setNombre(usuarioFront.getNombre());
        usuarioOk.setApellido(usuarioFront.getApellido());
        usuarioOk.setNombreUsuario(usuarioFront.getNombreUsuario());


        usuarioOk.setRoles(cambiarRolesListToSet(usuarioFront.getRoles()));
        usuarioService.save(usuarioOk);
        return new ResponseEntity<>(new Mensaje("Usuario actualizado"), HttpStatus.CREATED);
    }

    private List<Rol> cambiarSetToList(Set<Rol> roles){
        List<Rol> rolesCambiados = new ArrayList<>();
        roles.stream().forEach(r -> rolesCambiados.add(r));
        return rolesCambiados;
    }

    public List<String> cambiarRolesToString(List<Rol> roles){
        return roles.stream().map(Rol::getRolNombre).collect(Collectors.toList());
    }

    public Set<Rol> cambiarRolesListToSet(List<Rol> roles) {
        return roles.stream()
                .map(r -> {
                    Rol rolNuevo = new Rol(r.getId(), r.getRolNombre());
                    rolNuevo.setPermisos(r.getPermisos());
                    return rolNuevo;
                })
                .collect(Collectors.toSet());
    }

    @DeleteMapping("/eliminar-usuario/{nombreUsuario}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String nombreUsuario){
        Optional<Usuario> usuarioEncontrado = usuarioService.getByNombreUsuario(nombreUsuario);

        if(!usuarioEncontrado.isPresent()){
            return new ResponseEntity<>(new Mensaje("No existe usuario"), HttpStatus.NOT_FOUND);
        }

        usuarioEncontrado.get().getPedidos().forEach(p -> {
            p.setMozo(null);
            pedidosService.save(p);
        });

        usuarioService.eleminarUsuario(usuarioEncontrado.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/crear-rol")
    public ResponseEntity<?> crearRol(@RequestBody Rol rol) {

        Optional<Rol> rolEncontrado = rolService.getByRolNombre(rol.getRolNombre());
        if(rolEncontrado.isPresent()) return new ResponseEntity<>(new Mensaje("El rol ya existe"), HttpStatus.BAD_REQUEST);

        Rol rolCreado = rolService.save(rol);
        this.crearHistorialCuandoSeCreaRol(rolCreado);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/obtener-rol/{idRol}")
    public ResponseEntity<?> obtenerRol(@PathVariable Integer idRol) {
        Optional<Rol> rolEncontrado = rolService.buscarPorId(idRol);
        Response response = new Response();
        response.setData(rolEncontrado.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/actualizar-rol")
    public ResponseEntity<?> actualizarRol(@RequestBody Rol rol) {
        Optional<Rol> rolEncontrado = rolService.buscarPorId(rol.getId());
        Optional<Rol> rolPorNombreEncontrado = rolService.getByRolNombre(rol.getRolNombre());

        if(!rolEncontrado.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No se encontró rol"), HttpStatus.NOT_FOUND);
        }

        if(rolPorNombreEncontrado.isPresent() && rolEncontrado.get().getId() != rolPorNombreEncontrado.get().getId()) {
            return new ResponseEntity<>(new Mensaje("El rol ya existe"), HttpStatus.BAD_REQUEST);
        }

        Rol rolEncontradoGet = rolEncontrado.get();
        agregarHistorialAtributosRol(rolEncontradoGet, rol);
        rolEncontradoGet.setRolNombre(rol.getRolNombre());
        rolService.save(rolEncontradoGet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/obtener-historial-rol/{id_rol}")
    public ResponseEntity<?> obtenerHistorialUsuario(@PathVariable Integer id_rol){
        Response response = new Response();
        response.setData(rolService.listarHistorialRol(id_rol));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar-rol/{idRol}")
    public ResponseEntity<?> eliminarRol(@PathVariable Integer idRol) {
        Optional<Rol> rolEncontrado = rolService.buscarPorId(idRol);

        if(!rolEncontrado.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No se encontró rol"), HttpStatus.NOT_FOUND);
        }

        rolService.eliminarRolPorId(idRol);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/obtener-historial/{nombreUsuario}")
    public ResponseEntity<?> obtenerHistorialUsuario(@PathVariable String nombreUsuario){
        Response response = new Response();
        response.setData(usuarioService.listarHistorialUsuario(nombreUsuario));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/permisos")
    public ResponseEntity<?> listarTodosPermisos(){
        return new ResponseEntity<>(rolService.listarTodosPermisos(), HttpStatus.OK);
    }

    @GetMapping("/permisos/{idPermiso}")
    public ResponseEntity<?> obtenerPermiso(@PathVariable Long idPermiso){
        return new ResponseEntity<>(rolService.buscarPermisoPorId(idPermiso).get(), HttpStatus.OK);
    }

    @PutMapping("/permisos")
    public ResponseEntity<?> actualizarPermiso(@RequestBody Permiso permiso){

        return new ResponseEntity<>(rolService.guardar(permiso), HttpStatus.CREATED);
    }

    private void agregarHistorialRolesUsuario(Usuario usuarioActual, List<String> rolesFront) {
        HistoriaUsuario historial = new HistoriaUsuario();
        String detalle = "";
        List<String> rolesUsuarioActual = cambiarRolesToString(cambiarSetToList(usuarioActual.getRoles()));

        if(rolesUsuarioActual.size() > rolesFront.size()){
            detalle += "Se quitó el siguiente rol ";
            for (String rol: rolesUsuarioActual) {
                if(!rolesFront.contains(rol)){
                    detalle += rol + " ";
                }
            }
        }

        if(rolesFront.size() > rolesUsuarioActual.size()){
            detalle += "Se agregó el siguiente rol ";
            for (String rol: rolesFront) {
                if(!rolesUsuarioActual.contains(rol)){
                    detalle += rol + " ";
                }
            }
        }

        historial.setDetalle(detalle);
        historial.setUsuario(usuarioActual);
        usuarioService.crearHistoriaUsuario(historial);
    }

    private void agregarHistorialAtributosUsuario (Usuario usuarioActual, NuevoUsuario usuarioFront) {

        if(!usuarioActual.getNombre().equals(usuarioFront.getNombre())) {
            String detalle = "Se cambió el nombre de "+usuarioActual.getNombre()+" a " + usuarioFront.getNombre()+ "." ;
            nuevoHistorialUsuario(detalle, usuarioActual);
        }

        if(!usuarioActual.getApellido().equals(usuarioFront.getApellido())) {
            String detalle = "Se cambió el apellido de "+usuarioActual.getApellido()+" a " + usuarioFront.getApellido()+ "." ;
            nuevoHistorialUsuario(detalle, usuarioActual);
        }

        if(!usuarioActual.getEmail().equals(usuarioFront.getEmail())) {
            String detalle = "Se cambió el correo de "+usuarioActual.getEmail()+" a " + usuarioFront.getEmail()+ "." ;
            nuevoHistorialUsuario(detalle, usuarioActual);
        }

        if(!usuarioActual.getNombreUsuario().equals(usuarioFront.getNombreUsuario())) {
            String detalle = "Se cambió el usuario de "+usuarioActual.getNombreUsuario()+" a " + usuarioFront.getNombreUsuario()+ "." ;
            nuevoHistorialUsuario(detalle, usuarioActual);
        }

        }

    private void agregarHistorialAtributosRol (Rol rolActual, Rol rolFront) {

        if(!rolActual.getRolNombre().equals(rolFront.getRolNombre())) {
            String detalle = "Se cambió el nombre de "+rolActual.getRolNombre()+" a " + rolFront.getRolNombre()+ "." ;
            nuevoHistorialRol(detalle, rolActual);
        }

    }

        private String estadoToString(Boolean estado) {
            return estado ? "ACTIVO": "INACTIVO";
        }

        private void nuevoHistorialUsuario(String detalle, Usuario usuario) {
            HistoriaUsuario historial = new HistoriaUsuario();
            detalle = detalle.replace(".",". ");
            historial.setDetalle(detalle);
            historial.setUsuario(usuario);
            usuarioService.crearHistoriaUsuario(historial);
        }

        private void nuevoHistorialRol(String detalle, Rol rol) {
            HistorialRol historial = new HistorialRol();
            historial.setDetalle(detalle);
            historial.setRol(rol);
            rolService.crearHistoriaRol(historial);
        }

        private void crearHistorialCuandoSeCreaUsuario(Usuario usuario) {
            HistoriaUsuario historial = new HistoriaUsuario();
            historial.setDetalle("Se creó al usuario.");
            historial.setUsuario(usuario);
            usuarioService.crearHistoriaUsuario(historial);
        }

        private void crearHistorialCuandoSeCreaRol(Rol rol) {
            HistorialRol historial = new HistorialRol();
            historial.setDetalle("Se creó el rol.");
            historial.setRol(rol);
            rolService.crearHistoriaRol(historial);
        }

    }






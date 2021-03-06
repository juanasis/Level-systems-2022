package ar.edu.undec.level.security.controller;


import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.security.dto.*;
import ar.edu.undec.level.security.entity.HistoriaUsuario;
import ar.edu.undec.level.security.entity.Permiso;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.enums.RolNombre;
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
            return new ResponseEntity(new Mensaje("campos mal puestos o email inv??lido"), HttpStatus.BAD_REQUEST);
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
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

/*
* va a devolver un token, usando el nombre de usuario y contrase??a
* */
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
            return new ResponseEntity<>(new Mensaje("No se encontr?? el usuario: " + emailDto.getMailTo()), HttpStatus.NOT_FOUND);
        }

        Usuario usuarioEncontrado = usuario.get();

        emailDto.setMailFrom("anderson.bengolea@gmail.com");
        emailDto.setMailTo(usuarioEncontrado.getEmail());
        emailDto.setSubject("Restablecer contrase??a");
        emailDto.setUsername(usuarioEncontrado.getNombreUsuario());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        emailDto.setToken(tokenPassword);
        emailService.sendEmail(emailDto);
        usuarioEncontrado.setTokenPassword(tokenPassword);
        usuarioService.save(usuarioEncontrado);

        return new ResponseEntity<>(new Mensaje("Se envi?? un correo para restablecer la contrase??a."), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        if(!changePassword.getPassword().equals(changePassword.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Las contrase??as no coinciden"), HttpStatus.BAD_REQUEST);

        Optional<Usuario> usuario = usuarioService.findByTokenPassword(changePassword.getTokenPassword());
        if(!usuario.isPresent())
            return new ResponseEntity(new Mensaje("no existe usuario con esas credenciales"), HttpStatus.BAD_REQUEST);

        Usuario usuarioEncontrado = usuario.get();

        String newPassword = passwordEncoder.encode(changePassword.getPassword());
        usuarioEncontrado.setPassword(newPassword);
        usuarioEncontrado.setTokenPassword(null);
        usuarioService.save(usuarioEncontrado);

        return new ResponseEntity<>(new Mensaje("Contrase??a actualizada"), HttpStatus.OK);
    }

    @PutMapping("/actualizar-usuario")
    public ResponseEntity<?> actualizarUsuario(@RequestBody NuevoUsuario usuario){
        Optional<Usuario> usuarioEncontrado = usuarioService.getByNombreUsuario(usuario.getNombreUsuario());

        if(!usuarioEncontrado.isPresent()){
            return new ResponseEntity<>(new Mensaje("No existe usuario"), HttpStatus.NOT_FOUND);
        }

        Usuario usuarioOk = usuarioEncontrado.get();

        if(usuario.getRoles().size() != usuarioOk.getRoles().size()) {
            List<String> rolesUsuarioFront = cambiarRolesToString(usuario.getRoles());
            agregarHistorial(usuarioOk, rolesUsuarioFront);
        }

        usuarioOk.setNombre(usuario.getNombre());
        usuarioOk.setApellido(usuario.getApellido());

        usuarioOk.setRoles(cambiarRolesListToSet(usuario.getRoles()));
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

        rolService.save(rol);
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
            return new ResponseEntity<>(new Mensaje("No se encontr?? rol"), HttpStatus.NOT_FOUND);
        }

        if(rolPorNombreEncontrado.isPresent() && rolEncontrado.get().getId() != rolPorNombreEncontrado.get().getId()) {
            return new ResponseEntity<>(new Mensaje("El rol ya existe"), HttpStatus.BAD_REQUEST);
        }

        Rol rolEncontradoGet = rolEncontrado.get();
        rolEncontradoGet.setRolNombre(rol.getRolNombre());
        rolService.save(rolEncontradoGet);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/eliminar-rol/{idRol}")
    public ResponseEntity<?> eliminarRol(@PathVariable Integer idRol) {
        Optional<Rol> rolEncontrado = rolService.buscarPorId(idRol);

        if(!rolEncontrado.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No se encontr?? rol"), HttpStatus.NOT_FOUND);
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

    private void agregarHistorial(Usuario usuarioActual, List<String> rolesFront) {
        HistoriaUsuario historial = new HistoriaUsuario();
        String detalle = "";
        List<String> rolesUsuarioActual = cambiarRolesToString(cambiarSetToList(usuarioActual.getRoles()));

        if(rolesUsuarioActual.size() > rolesFront.size()){
            detalle += "Se quit?? el siguiente rol ";
            for (String rol: rolesUsuarioActual) {
                if(!rolesFront.contains(rol)){
                    detalle += rol + " ";
                }
            }
        }

        if(rolesFront.size() > rolesUsuarioActual.size()){
            detalle += "Se agreg?? el siguiente rol ";
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




}

package ar.edu.undec.level.security.controller;


import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.security.dto.*;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.enums.RolNombre;
import ar.edu.undec.level.security.jwt.JwtProvider;
import ar.edu.undec.level.security.service.RolService;
import ar.edu.undec.level.security.service.UsuarioService;
import ar.edu.undec.level.service.EmailService;
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

    //Esto espera un json, lo convierte en una clase java tipo Nuevo Usuario,
    //para validarlo BindingResult
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);

        Set<Rol> roles = new HashSet<>();

        if(nuevoUsuario.getRoles().get(0) != null){
            String rol = nuevoUsuario.getRoles().get(0);
            switch (rol){
                case "ROLE_ADMIN":
                    agregarRol(roles, RolNombre.ROLE_ADMIN);
                    break;
                case "ROLE_CAJERO":
                    agregarRol(roles, RolNombre.ROLE_CAJERO);
                    break;
                case "ROLE_CLIENTE":
                    agregarRol(roles, RolNombre.ROLE_CLIENTE);
                    break;
                case "ROLE_MOZO":
                    agregarRol(roles, RolNombre.ROLE_MOZO);
                    break;
                case "ROLE_COCINERO":
                    agregarRol(roles, RolNombre.ROLE_COCINERO);
                    break;
                default:
                    agregarRol(roles, RolNombre.ROLE_CLIENTE);
            }
        }


        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()));

        System.out.println(nuevoUsuario.getRoles().get(0));



        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

/*
* va a devolver un token, usando el nombre de usuario y contraseña
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

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDto emailDto) {

        Optional<Usuario> usuario = usuarioService.findByEmail(emailDto.getMailTo());

        if(!usuario.isPresent()) {
            return new ResponseEntity<>(new Mensaje("No se encontró el usuario: " + emailDto.getMailTo()), HttpStatus.NOT_FOUND);
        }

        Usuario usuarioEncontrado = usuario.get();

        emailDto.setMailFrom("juan.asis47@gmail.com");
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



    private Set<Rol> agregarRol(Set<Rol> roles, RolNombre rol) {
        roles.add(rolService.getByRolNombre(rol).get());
        return roles;
    }


}

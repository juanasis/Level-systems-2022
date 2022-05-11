package ar.edu.undec.level.security.service;
import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    // devolver usuario por nombre de usuario
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }
    public boolean existsByNombreUsuario(String nombreUsuario){
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> findByTokenPassword(String tokenPassword) {
        return usuarioRepository.findByTokenPassword(tokenPassword);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    public void eleminarUsuario(Integer idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}

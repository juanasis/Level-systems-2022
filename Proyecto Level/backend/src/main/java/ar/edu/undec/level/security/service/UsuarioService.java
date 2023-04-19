package ar.edu.undec.level.security.service;
import ar.edu.undec.level.security.entity.HistoriaUsuario;
import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.repository.HistoriaUsuarioRepository;
import ar.edu.undec.level.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private HistoriaUsuarioRepository historiaUsuarioRepository;

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

    public void eleminarUsuario(Integer idUsuario, String nombreUsuario) {

        List<HistoriaUsuario> historias = historiaUsuarioRepository.findByUsuario_NombreUsuario(nombreUsuario);

        if(!historias.isEmpty()) {
            historiaUsuarioRepository.deleteAll(historias);
        }
        usuarioRepository.deleteById(idUsuario);
    }

    public Usuario save(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public void crearHistoriaUsuario(HistoriaUsuario historiaUsuario){
        historiaUsuarioRepository.save(historiaUsuario);
    }

    public List<HistoriaUsuario> listarHistorialUsuario(String nombreUsuario) {
        return historiaUsuarioRepository.findByUsuario_NombreUsuario(nombreUsuario);
    }
}

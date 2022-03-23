package ar.edu.undec.level.security.repository;
import ar.edu.undec.level.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    //obtener usuario a partir de nombre de usuario
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByTokenPassword(String tokenPassword);
    //dos metodos para comprobar si existe el usuario
    boolean existsByNombreUsuario(String nombreUsuario);
    boolean existsByEmail(String email);

}

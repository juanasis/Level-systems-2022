package ar.edu.undec.level.security.repository;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    //encontrar por nombre del rol
    Optional<Rol> findByRolNombre(RolNombre rolNombre);

}

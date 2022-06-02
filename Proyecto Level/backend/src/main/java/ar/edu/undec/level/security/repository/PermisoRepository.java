package ar.edu.undec.level.security.repository;

import ar.edu.undec.level.security.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}

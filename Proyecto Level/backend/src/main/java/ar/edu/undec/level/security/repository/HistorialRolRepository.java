package ar.edu.undec.level.security.repository;


import ar.edu.undec.level.security.entity.HistorialRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialRolRepository extends JpaRepository<HistorialRol, Long> {

    List<HistorialRol> findByRol_Id(Integer rol_id);

    @Modifying
    @Query("DELETE FROM HistorialRol h WHERE h.rol.id = ?1")
    void eliminarHistorialDeUnRol(Integer rol_id);
}

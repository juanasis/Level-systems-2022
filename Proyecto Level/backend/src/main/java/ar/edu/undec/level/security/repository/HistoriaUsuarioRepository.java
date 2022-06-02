package ar.edu.undec.level.security.repository;

import ar.edu.undec.level.security.entity.HistoriaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriaUsuarioRepository extends JpaRepository<HistoriaUsuario, Long> {

    List<HistoriaUsuario> findByUsuario_NombreUsuario(String nombreUsuario);
}

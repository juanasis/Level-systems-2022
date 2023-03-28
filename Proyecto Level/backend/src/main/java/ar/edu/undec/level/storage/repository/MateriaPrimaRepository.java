package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.MateriaPrima;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MateriaPrimaRepository extends CrudRepository<MateriaPrima, Long> {
    Optional<MateriaPrima> findByNombreIgnoreCase(String nombre);
}

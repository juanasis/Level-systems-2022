package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.Receta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecetaRepository extends CrudRepository<Receta, Long> {

    @Query("FROM Receta r WHERE r.producto.id = ?1")
    Optional<Receta> obtenerRecetaPorProductId(Integer productoId);
}

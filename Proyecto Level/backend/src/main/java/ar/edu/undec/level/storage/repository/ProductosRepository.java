package ar.edu.undec.level.storage.repository;

 import ar.edu.undec.level.storage.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

 import java.util.List;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findAllByNombreContaining(String nombre);
    Producto findByNombre(String nombre);
        }
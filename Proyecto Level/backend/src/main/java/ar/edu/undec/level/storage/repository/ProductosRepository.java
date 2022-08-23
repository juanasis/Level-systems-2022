package ar.edu.undec.level.storage.repository;

 import ar.edu.undec.level.storage.entity.Categoria;
 import ar.edu.undec.level.storage.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.List;
 import java.util.Optional;

public interface ProductosRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findAllByNombreContaining(String nombre);
    Producto findByNombre(String nombre);
    List<Producto> findByCategoria_Id(Long id);

    @Query("FROM Categoria c where c.nombre = ?1")
    Optional<Categoria> findByNombreCategoria(String nombre);

    @Query("FROM Categoria")
    List<Categoria> getAllCategorias();

    List<Producto> findByNombreContaining(String filtroNombre);
}
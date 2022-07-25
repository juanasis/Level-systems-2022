package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {
        List<Pedido> findByFechaQueryAndMesa_Id(LocalDate fechaHoy, Integer idMesa);
        List<Pedido> findByFechaQueryAndMozo_Id(LocalDate fechaHoy, Integer idMozo);
        List<Pedido> findByFechaQuery(LocalDate fechaHoy);
}
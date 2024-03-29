package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {
        List<Pedido> findByFechaQueryAndMesa_Id(LocalDate fechaHoy, Integer idMesa);
        List<Pedido> findByFechaQueryAndMozo_Id(LocalDate fechaHoy, Integer idMozo);
        List<Pedido> findByFechaQuery(LocalDate fechaHoy);

        @Query("FROM Pedido p WHERE p.fechaQuery >= ?1 and p.fechaQuery <= ?2")
        List<Pedido> buscarPedidosRangoFecha(LocalDate fecha_desde, LocalDate fecha_hasta);

        @Query("FROM Pedido p WHERE p.fechaQuery >= ?1 and p.fechaQuery <= ?2 and p.estado= 4")
        List<Pedido> buscarPedidosPagadosPorRangoFecha(LocalDate fecha_desde, LocalDate fecha_hasta);

        int countByFechaQuery(LocalDate fechaHoy);

        @Query("FROM Pedido p WHERE p.fechaQuery= ?1 AND p.estado = ?2")
        List<Pedido> getListaPedidosHoy(LocalDate localDate, EstadoPedido estado);

}
package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.Caja;
import ar.edu.undec.level.storage.entity.EstadoCaja;
import ar.edu.undec.level.storage.entity.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CajaRepository extends CrudRepository<Caja, Long> {

    @Query("FROM Pedido p WHERE p.caja.idCaja = ?1")
    public List<Pedido> obtenerPedidosPorCaja(Long idCaja);

    @Query("FROM Caja c WHERE c.estado = ?1")
    Optional<Caja> buscarCajaActiva(EstadoCaja estado);
}

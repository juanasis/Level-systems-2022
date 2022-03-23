package ar.edu.undec.level.storage.repository;

import ar.edu.undec.level.storage.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

        }
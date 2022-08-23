package ar.edu.undec.level.mapper;

import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.service.UsuarioService;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CajaDtoToCaja implements IMapper{

    @Autowired
    private CajaRepository cajaRepository;

    @Override
    public Caja mapIn(CajaDtoIn cajaDtoIn) {
        Caja caja = new Caja();
        caja.setCajero(cajaDtoIn.getCajero());
        caja.setMonto_inicial(cajaDtoIn.getMonto_inicial());
        caja.setFecha_apertura(LocalDateTime.now());
        caja.setEstado(EstadoCaja.ABIERTO);
        return caja;
    }

    @Override
    public CajaDtoOut mapOut(Caja caja) {
        CajaDtoOut cajaDtoOut = new CajaDtoOut();
        cajaDtoOut.setIdCaja(caja.getIdCaja());
        cajaDtoOut.setMonto_inicial(caja.getMonto_inicial());
        cajaDtoOut.setMonto_final(caja.getMonto_final());
        cajaDtoOut.setEstado(caja.getEstado());
        cajaDtoOut.setFecha_apertura(caja.getFecha_apertura());
        cajaDtoOut.setFecha_cierre(caja.getFecha_cierre());
        List<Pedido> pedidos = cajaRepository.obtenerPedidosPorCaja(caja.getIdCaja());

        cajaDtoOut.setPedidos(pedidos.stream().map(p -> {
            p.setMozo(null);
            p.setCaja(null);
            return p;
        }).collect(Collectors.toList()));

        double montoTotalCierreCaja = pedidos.stream()
                .filter(p -> p.getEstado().equals(EstadoPedido.PAGADO))
                .mapToDouble(p -> obtenerMontoTotalPedido((List<ItemPedido>) p.getItemsList()))
                .sum();

        cajaDtoOut.setMonto_final(montoTotalCierreCaja+caja.getMonto_inicial());

        Usuario cajero = caja.getCajero();
        cajaDtoOut.setCajero(new Usuario());
        cajaDtoOut.getCajero().setId(cajero.getId());
        cajaDtoOut.getCajero().setNombre(cajero.getNombre());
        return cajaDtoOut;
    }

    private Double obtenerMontoTotalPedido(List<ItemPedido> items) {
        return items.stream().mapToDouble(i -> i.getProducto().getPrecio() * i.getCantidad()).sum();
    }

}

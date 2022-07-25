package ar.edu.undec.level.service;

import ar.edu.undec.level.mapper.CajaDtoToCaja;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private CajaDtoToCaja cajaMapper;

    public CajaDtoOut crearCaja(CajaDtoIn cajaDtoIn) {
        Optional<Caja> cajaActiva = cajaRepository.buscarCajaActiva(EstadoCaja.ABIERTO);

        if(cajaActiva.isPresent()) {
            throw new RuntimeException("Ya hay una caja activa.");
        }
        Caja cajaCreada = cajaRepository.save(cajaMapper.mapIn(cajaDtoIn));

        return cajaMapper.mapOut(cajaCreada);
    }

    public CajaDtoOut obtenerCajaActiva(Integer idCajero){
        Optional<Caja> cajaActiva = cajaRepository.buscarCajaActiva(EstadoCaja.ABIERTO);
        if(!cajaActiva.isPresent()) {
            throw new RuntimeException("No hay una caja activa.");
        }

        if(!Objects.equals(cajaActiva.get().getCajero().getId(), idCajero)){
            throw new RuntimeException("El cajero actual no tiene permisos para mostrar la caja activa.");
        }
        return cajaMapper.mapOut(cajaActiva.get());
    }

    public List<CajaDtoOut> listarCajas() {
        List<Caja> listaCajas = (List<Caja>) cajaRepository.findAll();
        return listaCajas.stream().map(cajaMapper::mapOut).collect(Collectors.toList());
    }


    public CajaDtoOut cerrarCaja(Long idCaja) {
        Caja caja = cajaRepository.findById(idCaja).get();

        List<Pedido> pedidos = cajaRepository.obtenerPedidosPorCaja(caja.getIdCaja());

        long cantidadPedidosSinTerminar = pedidos.stream()
                .filter(p ->
                        !p.getEstado().equals(EstadoPedido.CANCELADO) &&
                                !p.getEstado().equals(EstadoPedido.PAGADO)).count();

        if(cantidadPedidosSinTerminar > 0) {
            throw new RuntimeException("Hay pedidos sin terminar, termine los pedidos para poder cerrar caja.");
        }

        caja.setEstado(EstadoCaja.CERRADO);
        caja.setFecha_cierre(LocalDateTime.now());


        double montoTotalCierreCaja = pedidos.stream()
                .mapToDouble(p -> obtenerMontoTotalPedido((List<ItemPedido>) p.getItemsList()))
                .sum();

        caja.setMonto_final(montoTotalCierreCaja+caja.getMonto_inicial());

        return cajaMapper.mapOut(cajaRepository.save(caja));
    }

    private Double obtenerMontoTotalPedido(List<ItemPedido> items) {
        return items.stream().mapToDouble(i -> i.getProducto().getPrecio() * i.getCantidad()).sum();
    }


}

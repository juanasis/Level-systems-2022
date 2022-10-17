package ar.edu.undec.level.mapper;

import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.CajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MapperImpl implements IMapper{

    @Autowired
    private CajaRepository cajaRepository;

    @Override
    public Caja mapInCaja(CajaDtoIn cajaDtoIn) {
        Caja caja = new Caja();
        caja.setCajero(cajaDtoIn.getCajero());
        caja.setMonto_inicial(cajaDtoIn.getMonto_inicial());
        caja.setFecha_apertura(LocalDateTime.now());
        caja.setEstado(EstadoCaja.ABIERTO);
        return caja;
    }

    @Override
    public CajaDtoOut mapOutCajaDto(Caja caja) {
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

    @Override
    public Receta mapInReceta(RecetaDtoIn dtoIn) {

        if(Objects.isNull(dtoIn)) return null;

        Receta receta = new Receta();
        Producto producto = new Producto();
        producto.setId(dtoIn.getProductoId());
        receta.setProducto(producto);
        receta.setListaItemsReceta(mapInListaMateriaPrima(dtoIn.getListaItemsReceta()));

        return receta;
    }

    @Override
    public RecetaDtoOut mapOutRecetaDto(Receta receta) {
        if(Objects.isNull(receta)) return null;

        RecetaDtoOut recetaDtoOut = new RecetaDtoOut();
        recetaDtoOut.setNombreRecetaProducto(receta.getProducto().getNombre());
        recetaDtoOut.setListaRecetaItems(mapOutListaItemsReceta(receta.getListaItemsReceta()));

        return recetaDtoOut;
    }

    private List<ItemRecetaDto> mapOutListaItemsReceta(List<ItemReceta> listaItemsReceta) {
        if(Objects.isNull(listaItemsReceta)) return null;

        return listaItemsReceta.stream()
                .map(this::mapOutItemReceta)
                .collect(Collectors.toList());
    }

    private Double obtenerMontoTotalPedido(List<ItemPedido> items) {
        return items.stream().mapToDouble(i -> i.getProducto().getPrecio() * i.getCantidad()).sum();
    }

    private List<ItemReceta> mapInListaMateriaPrima(List<ItemRecetaDto> items) {
        if(Objects.isNull(items)) return null;

        return items.stream()
                .map(this::mapInItemReceta)
                .collect(Collectors.toList());
    }

    private ItemReceta mapInItemReceta(ItemRecetaDto dtoIn) {
        if(Objects.isNull(dtoIn)) return null;

        ItemReceta itemReceta = new ItemReceta();
        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setId(dtoIn.getMateriaPrimaId());
        itemReceta.setCantidad(dtoIn.getCantidad());
        itemReceta.setMateriaPrima(materiaPrima);
        return itemReceta;
    }

    private ItemRecetaDto mapOutItemReceta(ItemReceta itemReceta) {
        if(Objects.isNull(itemReceta)) return null;

        ItemRecetaDto itemRecetaDtoOut = new ItemRecetaDto();
        itemRecetaDtoOut.setCantidad(itemReceta.getCantidad());
        if(!Objects.isNull(itemReceta.getMateriaPrima())){
            itemRecetaDtoOut.setMateriaPrimaId(itemReceta.getMateriaPrima().getId());
            itemRecetaDtoOut.setNombreMateriaPrima(itemReceta.getMateriaPrima().getNombre());
            itemRecetaDtoOut.setDescripcionMateriaPrima(itemReceta.getMateriaPrima().getDescripcion());
        }

        return itemRecetaDtoOut;
    }


}

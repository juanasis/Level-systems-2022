package ar.edu.undec.level.service;

import ar.edu.undec.level.controller.dto.*;
import ar.edu.undec.level.security.dto.EmailDto;
import ar.edu.undec.level.security.repository.UsuarioRepository;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepo;
    @Autowired
    private ItemPedidoRepository itemPedidoRepo;
    @Autowired
    private ProductosRepository productosRepo;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private CajaRepository cajaRepository;

    static final Logger LOGGER = LoggerFactory.getLogger(PedidosService.class);


    public Response save(Pedido pedido) {
        Response response = new Response();
        pedido.setEstado(EstadoPedido.EN_COLA);

        try {
            Optional<Caja> cajaAbierta = cajaRepository.buscarCajaActiva(EstadoCaja.ABIERTO);
            if(!cajaAbierta.isPresent()) {
                throw new RuntimeException("No hay una caja activa, por favor aperture una caja para poder realizar un pedido");
            }
            Caja cajaEncontrada = new Caja();
            cajaEncontrada.setIdCaja(cajaAbierta.get().getIdCaja());
            pedido.setCaja(cajaEncontrada);
            Pedido pedidoGuardado = pedidosRepo.save(pedido);
           response.setData(pedidoGuardado);
       } catch (Exception e) {
           LOGGER.error(e.getMessage());
           e.printStackTrace();
           throw e;
       }
        return response;
    }

    public Response findAll() {
        Response  response = new Response();
        try {
            List<Pedido> pedidosList = pedidosRepo.findAll();
            List<PedidoDto> pedidosDto = new ArrayList<>();
            for (Pedido pedidoEntity: pedidosList                ) {
                 PedidoDto pedidoDto  = new PedidoDto(pedidoEntity);
                 pedidosDto.add(pedidoDto);
            }
            response.setData(pedidosDto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }
   public Response findOneById(String id) {
        Response response = new Response();
        try {
            Pedido pedido = pedidosRepo.findById(Integer.parseInt(id)).get();
            PedidoDto pedidoDto = new PedidoDto(pedido);
            response.setData(pedidoDto);

        } catch (NoSuchElementException e) {
            LOGGER.error("No existe.");
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    //Formatear fecha
    private Date nuevaFecha(){
        System.out.println(new Date());
        return new Date();
    }
    public Response update(Pedido pedido) {
        Response response = new Response();
        try {
            Pedido pedidoEncontrado = pedidosRepo.findById(pedido.getId()).get();
            Optional<Caja> cajaAbierta = cajaRepository.buscarCajaActiva(EstadoCaja.ABIERTO);
            if(!cajaAbierta.isPresent()) {
                throw new RuntimeException("No hay una caja activa, por favor aperture una caja.");
            }

            if(!cajaAbierta.get().getIdCaja().equals(pedidoEncontrado.getCaja().getIdCaja())) {
                throw new RuntimeException("No se puede puede actualizar pedidos de una caja cerrada.");
            }

            Caja cajaEncontrada = new Caja();
            cajaEncontrada.setIdCaja(cajaAbierta.get().getIdCaja());
            pedido.setCaja(cajaEncontrada);


            pedidoEncontrado.setTipoPago(pedido.getTipoPago());
            pedidoEncontrado.setEstado(pedido.getEstado());
            pedidoEncontrado.setItemsList(pedido.getItemsList());

            if(pedido.getEmailUsuario() != null) {
                emailService.enviarComprobante(pedido.getEmailUsuario(), pedidoEncontrado);
                pedidoEncontrado.setEmailUsuario(pedido.getEmailUsuario());
            }
            pedidosRepo.save(pedidoEncontrado);


            response.setData(pedidoEncontrado);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    public Response delete(Integer id) {
        Response response = new Response();
        try {
            Pedido pedido = pedidosRepo.findById(id).get();
            // setear campos
            pedidosRepo.save(pedido);

            response.setMessage("Eliminado correctamente.");

        } catch (NoSuchElementException e) {
            LOGGER.error("No existe.");
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error general.");
            throw e;
        }
        return response;

    }

    public Response findAllPedidosCocina() {//estado, mozo, mesa, hora, producto(cantidad, nombre, categoria)

        Response response = new Response();
        try {
            List<Pedido> pedidosList = pedidosRepo.findAll();
            List<PedidoDtoCocina> pedidosCocina = new ArrayList<>();
            for (Pedido pedidoEntity: pedidosList                ) {
                PedidoDtoCocina pedidoDtoCocina  = new PedidoDtoCocina(pedidoEntity);
                if(pedidoDtoCocina.getId() != null){
                    pedidosCocina.add(pedidoDtoCocina);
                }



            }

            response.setData(pedidosCocina);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }


    public Response buscarPorId(String id) {
        Response response = new Response();
        try {
            Pedido pedido = pedidosRepo.findById(Integer.parseInt(id)).get();

            List<ItemProductoDto> items ;
            items = getListaItemsProductoDto(pedido.getItemsList());
            response.setData(items);

        } catch (NoSuchElementException e) {
            LOGGER.error("No existe.");
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    private List<ItemProductoDto> getListaItemsProductoDto(Collection<ItemPedido> itemsList) {
        List<ItemProductoDto> itemProductoDtoList = new ArrayList<ItemProductoDto>();
        for (ItemPedido item: itemsList             ) {
            ItemProductoDto itemDTO = new ItemProductoDto();
            itemDTO.setNombre(item.getProducto().getNombre());
            itemDTO.setPrecio(BigDecimal.valueOf(item.getProducto().getPrecio()));
            itemDTO.setCantidad(item.getCantidad());
            itemProductoDtoList.add(itemDTO);
        }
        return itemProductoDtoList;
    }

    public List<Pedido> obtenerPedidosActivosDeMesa(Integer idMesa){
        LocalDate fechaActual = LocalDate.now();
        List<Pedido> pedidosEncontrados = pedidosRepo.findByFechaQueryAndMesa_Id(fechaActual, idMesa);
        return pedidosEncontrados.stream()
                .filter(p -> p.getEstado().equals(EstadoPedido.EN_COLA) || p.getEstado().equals(EstadoPedido.EN_PREPARACION) || p.getEstado().equals(EstadoPedido.LISTO) || p.getEstado().equals(EstadoPedido.ENTREGADO))
                .map(p -> {
                    p.setCaja(null);
                    return p;
                })
                .collect(Collectors.toList());
    }

    public List<Pedido> obtenerPedidosPorMesaPorMozo(Integer idMozo){
        LocalDate fechaActual = LocalDate.now();
        List<Pedido> pedidosEncontrados = pedidosRepo.findByFechaQueryAndMozo_Id(fechaActual, idMozo);
        System.out.println(pedidosEncontrados.size());

        return pedidosEncontrados.stream()
                .filter(p -> p.getEstado().equals(EstadoPedido.EN_COLA) || p.getEstado().equals(EstadoPedido.EN_PREPARACION) || p.getEstado().equals(EstadoPedido.LISTO) || p.getEstado().equals(EstadoPedido.ENTREGADO))
                .map(p -> {
                    p.setCaja(null);
                    return p;
                })
                .collect(Collectors.toList());
    }

    public List<Pedido> obtenerPedidosCocina(){
        LocalDate fechaActual = LocalDate.now();
        List<Pedido> pedidosEncontrados = pedidosRepo.findByFechaQuery(fechaActual);

        return pedidosEncontrados.stream()
                .filter(this::isPedidoCategoriaBebidaAndCategoriaDiferenteToBebida)
                .filter(p -> p.getEstado().equals(EstadoPedido.EN_COLA) || p.getEstado().equals(EstadoPedido.EN_PREPARACION) || p.getEstado().equals(EstadoPedido.LISTO) || p.getEstado().equals(EstadoPedido.ENTREGADO))
                .map(p -> {
                    p.setCaja(null);
                    return p;
                })
                .collect(Collectors.toList());
    }

    private boolean isPedidoCategoriaBebidaAndCategoriaDiferenteToBebida(Pedido pedido) {

        boolean categoriaBebidas = false;
        boolean categoriaDiferenteToBebidas = false;

        for (ItemPedido item : pedido.getItemsList()) {
            if(item.getProducto().getCategoria().getNombre().contains("BEBIDAS")){
                categoriaBebidas = true;
            } else {
                categoriaDiferenteToBebidas = true;
            }
        }

        return !(categoriaBebidas && !categoriaDiferenteToBebidas);
    }
}

package ar.edu.undec.level.service;

import ar.edu.undec.level.controller.dto.*;
import ar.edu.undec.level.security.repository.UsuarioRepository;
import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.ItemPedido;
import ar.edu.undec.level.storage.entity.Pedido;
import ar.edu.undec.level.storage.repository.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

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
    static final Logger LOGGER = LoggerFactory.getLogger(PedidosService.class);


    public Response save(PedidoRequest request) {
        Response response = new Response();
        Pedido entity = new Pedido();
        entity.setMozo(usuarioRepository.findById(request.getIdMozo()).get());
        entity.setMesa(mesaRepository.findById(request.getIdMesa()).get());
        entity.setEstado(EstadoPedido.ENCOLA);
        entity.setFecha(nuevaFecha());


        try {
           entity = pedidosRepo.save(entity);
           entity.setitemsList(getListaItems(request, entity));
           response.setData(entity);

       } catch (Exception e) {
           LOGGER.error(e.getMessage());
           e.printStackTrace();
           throw e;
       }
        return response;
    }

    private List<ItemPedido> getListaItems(PedidoRequest pedidoRequest, Pedido entity) {
        List<ItemPedido> result = new ArrayList<>();

        for (ItemPedidoDto itemPedidoDto: pedidoRequest.getItems()) {
            ItemPedido item = new ItemPedido(itemPedidoDto);
            item.setPedido(entity);
            System.out.println(itemPedidoDto.getProducto_id());
            item.setProducto(productosRepo.getOne(itemPedidoDto.getProducto_id()));
            item.setCantidad(itemPedidoDto.getCantidad());
            item.setPrecio(itemPedidoDto.getPrecio());
            result.add(item);
            itemPedidoRepo.save(item);
        }
        return result;
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
    public Response update(Object input) {
        Response response = new Response();
        try {

            response.setData(input);

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
}

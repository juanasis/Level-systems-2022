package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.PedidoRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.security.entity.Usuario;
import ar.edu.undec.level.security.service.UsuarioService;
import ar.edu.undec.level.service.MesasService;
import ar.edu.undec.level.service.PedidosService;
import ar.edu.undec.level.storage.entity.EstadoMesa;
import ar.edu.undec.level.storage.entity.Mesa;
import ar.edu.undec.level.storage.entity.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pedidos")
public class PedidosController {
    private static final Logger LOG = LoggerFactory.getLogger(PedidosController.class);

    @Autowired
    private PedidosService pedidosService;

    @Autowired
    private MesasService mesasService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/agregar")
    public ResponseEntity<Response> save(@Valid @RequestBody Pedido pedido  ){
        pedidosService.save(pedido);

        Mesa mesa = mesasService.buscarMesaPorId(pedido.getMesa().getId());
        mesa.setEstado(EstadoMesa.OCUPADA);

        mesa.setMozoId(pedido.getMozo().getId());
        mesasService.actualizar(mesa);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Response> getPedidos(){
        Response response;
        response = pedidosService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/cocina")
    public ResponseEntity<Response> getPedidosCocina(){
        Response response;
        response = pedidosService.findAllPedidosCocina();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable String id) {
        Response response = pedidosService.findOneById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/nro/{id}")
    public ResponseEntity<Response> getParaLaCaja(@PathVariable String id) {
        Response response = pedidosService.buscarPorId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/actualizar")
    public ResponseEntity<Response> put(@RequestBody Pedido pedido) {
        Response response = pedidosService.update(pedido);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") Integer pedidoId) {
        Response response = pedidosService.delete(pedidoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedidos-mesa/{idMesa}")
    public ResponseEntity<Response> obtenerPedidosActivosDeMesa(@PathVariable Integer idMesa){
        Response response = new Response();
        response.setData(pedidosService.obtenerPedidosActivosDeMesa(idMesa));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedidos-mozo/{idMozo}")
    public ResponseEntity<Response> obtenerPedidosActivosMozo(@PathVariable Integer idMozo){
        Response response = new Response();
        response.setData(pedidosService.obtenerPedidosPorMesaPorMozo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }







}

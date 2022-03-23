package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.PedidoRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.PedidosService;
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

    @PostMapping("/agregar")
    public ResponseEntity<Response> save(@Valid @RequestBody PedidoRequest request  ){
        Response response = pedidosService.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    @PutMapping
    public ResponseEntity<Response> put(@RequestBody Object input) {
        Response response = pedidosService.update(input);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") Integer pedidoId) {
        Response response = pedidosService.delete(pedidoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}

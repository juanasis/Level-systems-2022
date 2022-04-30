package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.ProductoRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.ProductosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductosService productosService;

    @GetMapping("/listar-categorias")
    public ResponseEntity<Response> listarCategorias() {
        Response response = new Response();
        response.setData(productosService.listarCategorias());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/productos-por-categoria/{idCategoria}")
    public ResponseEntity<Response> listarCategorias(@PathVariable Long idCategoria) {
        Response response = new Response();
        response.setData(productosService.listarProductosPorCategoria(idCategoria));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Response> getProductos(){
        Response response;
        response = productosService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> get(@PathVariable String id) {
        Response response = productosService.findOneById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProductoRequest request){
        Response response = productosService.save(id,request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregar")
    public ResponseEntity<Response> save(@Valid @RequestBody ProductoRequest request  ){
        Response response = productosService.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable(value = "id") Integer productoId) {
        Response response = productosService.delete(productoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Response> list(@PathVariable String nombre) {
        Response response = productosService.findByName(nombre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}

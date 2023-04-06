package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.ProductoRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.ProductosService;
import ar.edu.undec.level.storage.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductosService productosService;

    @GetMapping("/page/{page}")
    public ResponseEntity<Response> listarProductosPageable(@PathVariable Integer page) {

        Pageable pageable = PageRequest.of(page, 5);
        Response response = new Response();
        response.setData(productosService.listarProductosPageable(pageable));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filtrar/{filtroNombre}")
    public ResponseEntity<Response> filtrarProductosPorNombre(@PathVariable String filtroNombre) {

        Response response = new Response();
        response.setData(productosService.buscarProductosPorNombre(filtroNombre));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
    public ResponseEntity<Response> get(@PathVariable Integer id) {
        Response response = productosService.findOneById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Producto producto){
        Response response = productosService.editarProducto(id,producto);

        if(response == null) return new ResponseEntity<>(new Mensaje("El producto ya existe"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> save(@Valid @RequestBody Producto producto  ){
        Producto productoCreado = productosService.crearProducto(producto);

        if(productoCreado == null) return new ResponseEntity<>(new Mensaje("El producto ya existe"), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Integer productoId) throws IOException {
        productosService.delete(productoId);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Response> list(@PathVariable String nombre) {
        Response response = productosService.findByName(nombre);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}

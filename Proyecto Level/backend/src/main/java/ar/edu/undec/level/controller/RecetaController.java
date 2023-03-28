package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.service.RecetaService;
import ar.edu.undec.level.storage.entity.RecetaDtoIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearReceta(@RequestBody RecetaDtoIn recetaDtoIn){
        recetaService.crearReceta(recetaDtoIn);
        return new ResponseEntity<>(new Mensaje("Receta creada!"),HttpStatus.CREATED);
    }

    @GetMapping("/buscar-por-producto-id/{productoId}")
    public ResponseEntity<?> buscarRecetaPorProductoId(@PathVariable Integer productoId ){
        return new ResponseEntity<>(recetaService.obtenerRecetaPorProductId(productoId) ,HttpStatus.OK);
    }
    @PutMapping("/{productoId}")
    public ResponseEntity<?> actualizarReceta(@PathVariable Integer productoId, @RequestBody RecetaDtoIn recetaDtoIn){
        return new ResponseEntity<>(recetaService.actualizarReceta(productoId, recetaDtoIn) ,HttpStatus.OK);
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<?> eliminarReceta(@PathVariable Integer productoId){
        recetaService.eliminarReceta(productoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

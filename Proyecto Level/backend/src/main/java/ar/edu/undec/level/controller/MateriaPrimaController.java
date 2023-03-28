package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.MateriaPrimaService;
import ar.edu.undec.level.storage.entity.CajaDtoIn;
import ar.edu.undec.level.storage.entity.MateriaPrima;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/materias-prima")
public class MateriaPrimaController {

    @Autowired
    private MateriaPrimaService materiaPrimaService;

    @GetMapping("/buscar/{idMateriaPrima}")
    public ResponseEntity<?> buscarMateriaPrimaPorId(@PathVariable Long idMateriaPrima) {
        Response response = new Response();
        response.setData(materiaPrimaService.buscarPorIdMateriaPrima(idMateriaPrima));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/actualizar/{idMateriaPrima}")
    public ResponseEntity<?> actualizarMateriaPrima(@PathVariable Long idMateriaPrima, @RequestBody MateriaPrima materiaPrima) {

        MateriaPrima materiaPrimaEncontrada = materiaPrimaService.buscarPorIdMateriaPrima(idMateriaPrima);

        Response response = new Response();
        response.setData(materiaPrimaService.actualizarMateriaPrima(materiaPrima, materiaPrimaEncontrada));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarMateriasPrima() {
        Response response = new Response();
        response.setData(materiaPrimaService.obtenerListaMateriaPrima());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearMateriaPrima(@RequestBody MateriaPrima materiaPrima) {
        materiaPrimaService.crearMaterialPrima(materiaPrima);
        return new ResponseEntity<>(new Mensaje("Se creó correctamente la materia prima"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{materia-prima-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMateriaPrima(@PathVariable(name = "materia-prima-id") Long materiaPrimaId) {
        materiaPrimaService.eliminarPorIdMateriaPrima(materiaPrimaId);
    }
}

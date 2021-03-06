package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.CajaService;
import ar.edu.undec.level.storage.entity.CajaDtoIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cajas")
public class CajaController {

    @Autowired
    private CajaService cajaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCaja(@RequestBody CajaDtoIn cajaDtoIn) {
        Response response = new Response();
        response.setData(cajaService.crearCaja(cajaDtoIn));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/buscar-caja-activa/cajero/{idCajero}")
    public ResponseEntity<?> obtenerCajaPorId(@PathVariable Integer idCajero) {
        Response response = new Response();
        response.setData(cajaService.obtenerCajaActiva(idCajero));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarCajas() {
        Response response = new Response();
        response.setData(cajaService.listarCajas());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/cerrar-caja/{idCaja}")
    public ResponseEntity<?> cerrarCaja(@PathVariable Long idCaja) {
        Response response = new Response();
        response.setData(cajaService.cerrarCaja(idCaja));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

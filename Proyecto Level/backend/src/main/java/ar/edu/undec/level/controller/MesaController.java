package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.MesaRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.MesasService;
import ar.edu.undec.level.storage.entity.Mesa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/mesas")
public class MesaController {
    private static final Logger LOG = LoggerFactory.getLogger(MesaController.class);

    @Autowired
    private MesasService mesasService;

    @PostMapping("/agregar")
    public ResponseEntity<?> save(@Valid @RequestBody Mesa mesa  ){

        Response response = mesasService.save(mesa);

        if(response == null) return new ResponseEntity<>(new Mensaje("La mesa ya existe."), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> update(@Valid @RequestBody Mesa mesa  ){

        Response response = mesasService.actualizar(mesa);

        if(response == null) return new ResponseEntity<>(new Mensaje("La mesa ya existe."), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping()
    public ResponseEntity<Response> getPedidos(){
        Response response;
        response = mesasService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/buscar/{idMesa}")
    public ResponseEntity<Mesa> buscarMesaPorId(@PathVariable Integer idMesa) {
        return new ResponseEntity<>(mesasService.buscarMesaPorId(idMesa), HttpStatus.OK);
    }



}

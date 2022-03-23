package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.MesaRequest;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.MesasService;
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
    public ResponseEntity<Response> save(@Valid @RequestBody MesaRequest request  ){
        Response response = mesasService.save(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<Response> getPedidos(){
        Response response;
        response = mesasService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}

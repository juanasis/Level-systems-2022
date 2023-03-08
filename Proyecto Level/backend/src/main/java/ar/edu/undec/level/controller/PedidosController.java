package ar.edu.undec.level.controller;

import ar.edu.undec.level.controller.dto.Mensaje;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.service.MesasService;
import ar.edu.undec.level.service.PdfGeneradorService;
import ar.edu.undec.level.service.PedidosService;
import ar.edu.undec.level.storage.entity.EstadoMesa;
import ar.edu.undec.level.storage.entity.Mesa;
import ar.edu.undec.level.storage.entity.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

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
    private PdfGeneradorService pdfGeneratorService;

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

    @PutMapping("/actualizar/estado-bebida")
    public ResponseEntity<?> actualizarPedidoEstadoBebida(@RequestBody Pedido pedido) {

        pedidosService.actualizarPedidoEstadoBebida(pedido);

        return new ResponseEntity<>(new Mensaje("Pedido actualizado"), HttpStatus.OK);
    }

    @PutMapping("/actualizar/items")
    public ResponseEntity<?> actualizarItemsPedido(@RequestBody Pedido pedido) {

        pedidosService.actualizarItemPedido(pedido);

        return new ResponseEntity<>(new Mensaje("Pedido actualizado"), HttpStatus.OK);
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

    @GetMapping("/pedidos-por-rango-fecha")
    public ResponseEntity<Response> pedidosPorRangoFecha(@RequestParam("fecha_desde")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                     LocalDate fecha_desde,
                                                         @RequestParam("fecha_hasta")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 LocalDate fecha_hasta){
        Response response = new Response();
        response.setData(pedidosService.listarPedidosPorRangoFecha(fecha_desde, fecha_hasta));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<Response> topTresProductosMasVendidos(@RequestParam("fecha_desde")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 LocalDate fecha_desde,
                                                         @RequestParam("fecha_hasta")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 LocalDate fecha_hasta){
        Response response = new Response();
        response.setData(pedidosService.topTresProductosMasVendidos(fecha_desde, fecha_hasta));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedidos-mozo/{idMozo}")
    public ResponseEntity<Response> obtenerPedidosActivosMozo(@PathVariable Integer idMozo){
        Response response = new Response();
        response.setData(pedidosService.obtenerPedidosPorMesaPorMozo(idMozo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/pedidos-cocina")
    public ResponseEntity<Response> obtenerPedidosActivosCocina(){
        Response response = new Response();
        response.setData(pedidosService.obtenerPedidosCocina());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cantidad-pedidos-hoy")
    public ResponseEntity<Response> getCantidadPedidosHoy() {
        Response response = new Response();
        response.setData(pedidosService.getCantidadPedidosDelDia());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/total-pedidos-ingresos")
    public ResponseEntity<Response> getTotalPedidosIngresos() {
        Response response = new Response();
        response.setData(pedidosService.getIngresosDelDiaPorPedidosPagado());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/boleta/{pedidoId}")
    public void generarBoleta(HttpServletResponse response, @PathVariable Long pedidoId) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Comprobante de pago.pdf";
        response.setHeader(headerKey, headerValue);

        pdfGeneratorService.crearBoleta(response, pedidoId);
    }

    @GetMapping("/reporte-mozos")
    public ResponseEntity<?> reporte(@RequestParam("fecha_desde")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         LocalDate fecha_desde,
                                     @RequestParam("fecha_hasta")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                         LocalDate fecha_hasta) {

        Response response = new Response();
        response.setData(pedidosService.obtenerReporteMozos(fecha_desde, fecha_hasta));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

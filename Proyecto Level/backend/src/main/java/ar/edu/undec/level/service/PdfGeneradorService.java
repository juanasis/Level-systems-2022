package ar.edu.undec.level.service;

import ar.edu.undec.level.storage.entity.Pedido;
import ar.edu.undec.level.storage.repository.PedidosRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneradorService {

    @Autowired
    private PedidosRepository pedidosRepository;


    public void crearBoleta(HttpServletResponse response, Long pedidoId) throws IOException, DocumentException {

        Pedido pedido =  pedidosRepository.findById(pedidoId.intValue()).get();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        PdfPTable boleta = new PdfPTable(4);

        PdfPTable boletaTitulo = new PdfPTable(1);
        Font fuelteTitulo = FontFactory.getFont("Helvetica",16);
        PdfPCell celda = new PdfPCell(new Phrase("BAMBU RESTO", fuelteTitulo));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(8);
        celda.setBorderWidthLeft(0);
        celda.setBorderWidthRight(0);
        celda.setBorderWidthTop(0);
        celda.setBorderWidthBottom(0);
        boletaTitulo.addCell(celda);

        PdfPTable boletaDatos = new PdfPTable(2);

        PdfPCell celdaComprobante = new PdfPCell(new Phrase("Comprobante de pago: #"+pedido.getId()));
        celdaComprobante.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaComprobante.setBorderWidthLeft(0);
        celdaComprobante.setBorderWidthRight(0);
        celdaComprobante.setBorderWidthTop(0);
        celdaComprobante.setBorderWidthBottom(0);
        boletaDatos.addCell(celdaComprobante);

        String fechaString = pedido.getFecha().toString();
        LocalDateTime inputDateTime = LocalDateTime.parse(fechaString);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaConvertida = inputDateTime.format(outputFormatter);

        PdfPCell celdaFecha = new PdfPCell(new Phrase("Fecha y hora: "+fechaConvertida));
        celdaFecha.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaFecha.setBorderWidthLeft(0);
        celdaFecha.setBorderWidthRight(0);
        celdaFecha.setBorderWidthTop(0);
        celdaFecha.setBorderWidthBottom(0);
        boletaDatos.addCell(celdaFecha);

        PdfPCell celdaMesa = new PdfPCell(new Phrase("Mesa: "+pedido.getMesa().getNombre()));
        celdaMesa.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaMesa.setBorderWidthLeft(0);
        celdaMesa.setBorderWidthRight(0);
        celdaMesa.setBorderWidthTop(0);
        celdaMesa.setPaddingTop(8);
        celdaMesa.setPaddingBottom(8);
        boletaDatos.addCell(celdaMesa);

        PdfPCell celdaMozo = new PdfPCell(new Phrase("Mozo: "+pedido.getMozo().getNombre()));
        celdaMozo.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaMozo.setBorderWidthLeft(0);
        celdaMozo.setBorderWidthRight(0);
        celdaMozo.setBorderWidthTop(0);
        celdaMozo.setPaddingBottom(8);
        celdaMozo.setPaddingTop(8);
        boletaDatos.addCell(celdaMozo);

        boleta.addCell(agregarCelda("Cantidad"));
        boleta.addCell(agregarCelda("Producto"));
        boleta.addCell(agregarCelda("Precio U."));
        boleta.addCell(agregarCelda("Importe"));
        pedido.getItemsList().forEach(item -> {
            PdfPCell celdaCantidad = new PdfPCell(new Phrase("x" + item.getCantidad()));
            celdaCantidad.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaCantidad.setBorderWidthBottom(0);
            celdaCantidad.setBorderWidthRight(0);
            celdaCantidad.setBorderWidthLeft(0);
            celdaCantidad.setBorderWidthTop(0);
            celdaCantidad.setPaddingTop(8);
            celdaCantidad.setPaddingBottom(8);
            boleta.addCell(celdaCantidad);

            PdfPCell celdaNombre = new PdfPCell(new Phrase(item.getProducto().getNombre()));
            celdaNombre.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaNombre.setBorderWidthBottom(0);
            celdaNombre.setBorderWidthRight(0);
            celdaNombre.setBorderWidthLeft(0);
            celdaNombre.setBorderWidthTop(0);
            celdaNombre.setPaddingTop(8);
            celdaNombre.setPaddingBottom(8);
            boleta.addCell(celdaNombre);


            PdfPCell celdaPrecioUnitario = new PdfPCell(new Phrase("$ "+item.getProducto().getPrecio()));
            celdaPrecioUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaPrecioUnitario.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaPrecioUnitario.setBorderWidthBottom(0);
            celdaPrecioUnitario.setBorderWidthRight(0);
            celdaPrecioUnitario.setBorderWidthLeft(0);
            celdaPrecioUnitario.setBorderWidthTop(0);
            celdaPrecioUnitario.setPaddingTop(8);
            celdaPrecioUnitario.setPaddingBottom(8);
            boleta.addCell(celdaPrecioUnitario);

            PdfPCell celdaPrecio = new PdfPCell(new Phrase("$ "+item.getCantidad()*item.getProducto().getPrecio()));
            celdaPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
            celdaPrecio.setBorderWidthBottom(0);
            celdaPrecio.setBorderWidthLeft(0);
            celdaPrecio.setBorderWidthRight(0);
            celdaPrecio.setBorderWidthTop(0);
            celdaPrecio.setPaddingTop(8);
            celdaPrecio.setPaddingBottom(8);
            boleta.addCell(celdaPrecio);
        });



        PdfPCell celdaVacia = new PdfPCell();
        celdaVacia.setBorderWidthBottom(0);
        celdaVacia.setBorderWidthRight(0);
        celdaVacia.setBorderWidthLeft(0);
        boleta.addCell(celdaVacia);

        PdfPCell celdaVacia1 = new PdfPCell();
        celdaVacia1.setBorderWidthBottom(0);
        celdaVacia1.setBorderWidthRight(0);
        celdaVacia1.setBorderWidthLeft(0);
        boleta.addCell(celdaVacia1);

        PdfPCell celdaTotalTexto = new PdfPCell(new Phrase("Total"));
        celdaTotalTexto.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaTotalTexto.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTotalTexto.setBorderWidthBottom(0);
        celdaTotalTexto.setBorderWidthRight(0);
        celdaTotalTexto.setBorderWidthLeft(0);
        boleta.addCell(celdaTotalTexto);

        PdfPCell celdaTotal = new PdfPCell(new Phrase("$ "+pedido.getTotal()));
        celdaTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celdaTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTotal.setBorderWidthBottom(0);
        celdaTotal.setBorderWidthRight(0);
        celdaTotal.setBorderWidthLeft(0);
        boleta.addCell(celdaTotal);

        document.add(boletaTitulo);
        document.add(boletaDatos);
        document.add(boleta);
        document.setPageSize(PageSize.LETTER.rotate());

        document.close();
    }

    private PdfPCell agregarCelda(String texto){
        PdfPCell celda = new PdfPCell(new Phrase(texto));
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBorderWidthRight(0);
        celda.setBorderWidthLeft(0);
        celda.setBorderWidthTop(0);

        celda.setPaddingTop(8);
        celda.setPaddingBottom(8);
        return celda;
    }
}

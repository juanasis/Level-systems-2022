package ar.edu.undec.level.service;

import ar.edu.undec.level.mapper.IMapper;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.ProductosRepository;
import ar.edu.undec.level.storage.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class RecetaService {


    private final RecetaRepository recetaRepository;

    private final ProductosRepository productosRepository;

    private final IMapper mapper;

    public RecetaService(RecetaRepository recetaRepository, ProductosRepository productosRepository, IMapper mapper) {
        this.recetaRepository = recetaRepository;
        this.productosRepository = productosRepository;
        this.mapper = mapper;
    }

    public RecetaDtoOut obtenerRecetaPorProductId(Integer productoId) {
        Optional<Receta> recetaEncontrada = recetaRepository.obtenerRecetaPorProductId(productoId);
        if(!recetaEncontrada.isPresent()) throw new RuntimeException("No existe una receta para el producto con el ID " + productoId);

        return mapper.mapOutRecetaDto(recetaEncontrada.get());
    }

    public void descontarStockMateriaPrima(Integer productoId, Integer cantidadItemPedido) {
        Optional<Receta> recetaEncontrada = recetaRepository.obtenerRecetaPorProductId(productoId);

        recetaEncontrada.ifPresent(r -> {
            r.getListaItemsReceta().forEach(item -> {
                item.getMateriaPrima().setStock(item.getMateriaPrima().getStock() - (cantidadItemPedido * item.getCantidad()));
            });
        });
    }

    public void crearReceta(RecetaDtoIn dtoIn) {
        Optional<Producto> productoEncontrado = productosRepository.findById(dtoIn.getProductoId());
        if(!productoEncontrado.isPresent()) throw new RuntimeException("No existe producto con el ID: "+dtoIn.getProductoId());

        Optional<Receta> recetaEncontrada = recetaRepository.obtenerRecetaPorProductId(dtoIn.getProductoId());
        if(recetaEncontrada.isPresent()) throw new RuntimeException("Ya existe una receta para el producto "+recetaEncontrada.get().getProducto().getNombre());

        recetaRepository.save(mapper.mapInReceta(dtoIn));
    }
}

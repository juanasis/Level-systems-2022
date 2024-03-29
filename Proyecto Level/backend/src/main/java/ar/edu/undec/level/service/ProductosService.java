package ar.edu.undec.level.service;

import ar.edu.undec.level.controller.dto.Response;

import ar.edu.undec.level.mapper.MapperImpl;
import ar.edu.undec.level.storage.entity.*;
import ar.edu.undec.level.storage.repository.ProductosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ProductosService {
    @Autowired
    private ProductosRepository productosRepo;

    @Autowired
    private SubirImagenService subirImagenService;
    static final Logger LOGGER = LoggerFactory.getLogger(ProductosService.class);

    public List<Categoria> listarCategorias(){
        return productosRepo.getAllCategorias();
    }

    public List<Producto> listarProductosPorCategoria(Long id){
        return productosRepo.findByCategoria_Id(id);
    }

    public List<Producto> buscarProductosPorNombre(String filtroNombre){
        return productosRepo.findAllByNombreContaining(filtroNombre);
    }

    public Response findAll() {
        Response  response = new Response();
        List<Producto> productoList = productosRepo.findAll();
        if(productoList.isEmpty())
            response.setMessage("No hay Productos");
        else
            response.setData(productoList);
        return response;
    }

    public Page<Producto> listarProductosPageable(Pageable pageable) {
        return productosRepo.findAll(pageable);
    }

    public Response findOneById(Integer id) {
        Response response = new Response();
        try {

            response.setData(productosRepo.findById(id).get());

        } catch (NoSuchElementException e) {
            LOGGER.error("No existe.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }
    public Response editarProducto(Integer id, Producto producto) {

        Optional<Producto> productoEncontrado = productosRepo.findById(id);

        if(!productoEncontrado.isPresent()) {
            throw new RuntimeException("No existe producto con el ID: " + id);
        }

        if(!producto.getNombre().trim().equals(productoEncontrado.get().getNombre().trim())) {
            Producto productoEncontradoPorNombre = productosRepo.findByNombreIgnoreCase(producto.getNombre());
            if(productoEncontradoPorNombre != null) {
                return null;
            }

        }



        Response response = new Response();
        Producto productoGet = productoEncontrado.get();

        productoGet.setNombre(producto.getNombre());
        productoGet.setPrecio(producto.getPrecio());
        productoGet.setCategoria(producto.getCategoria());
        productoGet.setDescripcion(producto.getDescripcion());
        productoGet.setEstado(producto.getEstado());

        productosRepo.save(productoGet);
        response.setData("Produto Actualizado");
        return response;
    }
    public Response cambiarEstado(int id, EstadoProducto nuevoEstado) {
        Response response = new Response();
        Producto entity = new Producto();
        if (id >= 0) {
            entity = productosRepo.getOne(id);
            entity.setEstado(nuevoEstado);

        }
        productosRepo.save(entity);
        response.setData("guardado");
        return response;
    }

//    public Response save(ProductoRequest productos) {
//        return save(-1,productos);
//    }

    public Producto crearProducto(Producto producto) {
        Producto productoEncontrado = productosRepo.findByNombreIgnoreCase(producto.getNombre());
        if(productoEncontrado != null) return null;
        producto.setEstado(EstadoProducto.DISPONIBLE);
        return productosRepo.save(producto);
    }

    private Date nuevaFecha(){
        System.out.println(new Date());
        return new Date();
    }

    public void delete(Integer id) throws IOException {
        Producto productoEncontrado = productosRepo.findById(id).get();
        try{
            productosRepo.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El producto no se puede eliminar");
        }
        subirImagenService.eliminarImagen(productoEncontrado.getImgpath());

    }
    public Response findByName(String nombre) {
        Response response = new Response();
        try {
            List<Producto> productoList = productosRepo.findAllByNombreContaining(nombre);
            response.setData(productoList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    public boolean existsById(int id){
        return productosRepo.existsById(id);
    }

    public Optional<Producto> getOne(int id){
        return productosRepo.findById(id);
    }


}

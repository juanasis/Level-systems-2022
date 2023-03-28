package ar.edu.undec.level.service;

import ar.edu.undec.level.storage.entity.MateriaPrima;
import ar.edu.undec.level.storage.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    public void crearMaterialPrima(MateriaPrima materiaPrima) {

        Optional<MateriaPrima> materiaEncontrada = materiaPrimaRepository.findByNombreIgnoreCase(materiaPrima.getNombre());

        if(materiaEncontrada.isPresent()) throw new RuntimeException("Ya existe materia prima con el nombre "+materiaPrima.getNombre());

        materiaPrimaRepository.save(materiaPrima);
    }

    public MateriaPrima buscarPorIdMateriaPrima(Long id) {
        Optional<MateriaPrima> materiaPrimaEncontrada = materiaPrimaRepository.findById(id);

        if(!materiaPrimaEncontrada.isPresent()) {
            throw new RuntimeException("No se encontró materia prima con el ID: "+id);
        }

        return actualizarEstadoCantidadMinima(materiaPrimaEncontrada.get());
    }

    public MateriaPrima actualizarMateriaPrima(MateriaPrima materiaPrimaFront, MateriaPrima materiaPrimaEncontrada) {

        if(!materiaPrimaEncontrada.getNombre().trim().equals(materiaPrimaFront.getNombre().trim())) {
            Optional<MateriaPrima> materiaEncontrada = materiaPrimaRepository.findByNombreIgnoreCase(materiaPrimaFront.getNombre());
            if(materiaEncontrada.isPresent())throw new RuntimeException("Ya existe materia prima con el nombre "+materiaPrimaFront.getNombre());
        }

        materiaPrimaEncontrada.setNombre(materiaPrimaFront.getNombre());
        materiaPrimaEncontrada.setStock(materiaPrimaFront.getStock());
        materiaPrimaEncontrada.setCantidadMinima(materiaPrimaFront.getCantidadMinima());
        materiaPrimaEncontrada.setDescripcion(materiaPrimaFront.getDescripcion());

        return materiaPrimaRepository.save(materiaPrimaEncontrada);
    }

    public void eliminarPorIdMateriaPrima(Long id) {
        Optional<MateriaPrima> materiaPrimaEncontrada = materiaPrimaRepository.findById(id);

        if(!materiaPrimaEncontrada.isPresent()) {
            throw new RuntimeException("No se encontró materia prima con el ID: "+id);
        }

        try{
            materiaPrimaRepository.deleteById(materiaPrimaEncontrada.get().getId());
        }catch (DataIntegrityViolationException e) {
            throw new RuntimeException("La materia prima no se puede eliminar");
        }

    }

    public List<MateriaPrima> obtenerListaMateriaPrima() {
        List<MateriaPrima> listaMateriaPrima = (List<MateriaPrima>) materiaPrimaRepository.findAll();

        return listaMateriaPrima.stream().map(this::actualizarEstadoCantidadMinima)
                .collect(Collectors.toList());
    }

    private MateriaPrima actualizarEstadoCantidadMinima(MateriaPrima materiaPrima) {
        materiaPrima.setAlertaCantidad(materiaPrima.getStock() <= materiaPrima.getCantidadMinima());
        return materiaPrima;
    }

}

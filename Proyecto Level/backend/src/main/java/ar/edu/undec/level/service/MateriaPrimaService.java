package ar.edu.undec.level.service;

import ar.edu.undec.level.storage.entity.MateriaPrima;
import ar.edu.undec.level.storage.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    public void crearMaterialPrima(MateriaPrima materiaPrima) {
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

        materiaPrimaRepository.deleteById(materiaPrimaEncontrada.get().getId());
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

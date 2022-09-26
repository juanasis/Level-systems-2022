package ar.edu.undec.level.service;


import ar.edu.undec.level.controller.MesaController;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.storage.entity.EstadoMesa;
import ar.edu.undec.level.storage.entity.EstadoPedido;
import ar.edu.undec.level.storage.entity.Mesa;
import ar.edu.undec.level.storage.entity.Pedido;
import ar.edu.undec.level.storage.repository.MesaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
@Service
public class MesasService {
    private static final Logger LOG = LoggerFactory.getLogger(MesaController.class);

    @Autowired
    private MesaRepository mesaRepository;

    public Response save(Mesa mesa) {
        Response response = new Response();
        mesa.setEstado(EstadoMesa.LIBRE);
        mesaRepository.save(mesa);
        response.setData(mesa);
        return response;
    }

    public Response actualizar(Mesa mesa) {
        Response response = new Response();
        Mesa mesaEncontrada = mesaRepository.findById(mesa.getId()).get();
        BeanUtils.copyProperties(mesa, mesaEncontrada);
        mesaRepository.save(mesaEncontrada);
        response.setData(mesaEncontrada);
        return response;
    }

    public Mesa buscarMesaPorId(Integer id) {
        return mesaRepository.findById(id).orElse(null);
    }

    public Response findAll() {
        Response  response = new Response();
        try {
            List<Mesa> mesaList = mesaRepository.findAll();
            mesaList.forEach(m -> {
                List<Pedido> pedidosActivos = m.getPedidos().stream()
                        .filter(p ->
                                p.getEstado().equals(EstadoPedido.EN_PREPARACION) ||
                                        p.getEstado().equals(EstadoPedido.EN_COLA )||
                                        p.getEstado().equals(EstadoPedido.LISTO) ||
                                        p.getEstado().equals(EstadoPedido.ENTREGADO))
                        .collect(Collectors.toList());

                if(pedidosActivos.size() > 0) {
                    m.setEstado(EstadoMesa.OCUPADA);
                }else {
                    m.setEstado(EstadoMesa.LIBRE);
                    m.setMozoId(null);
                }
            });
            List<Mesa> mesas = mesaRepository.saveAll(mesaList);
            response.setData(mesas);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }
}

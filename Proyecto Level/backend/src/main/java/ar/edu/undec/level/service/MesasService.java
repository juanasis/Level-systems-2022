package ar.edu.undec.level.service;


import ar.edu.undec.level.controller.MesaController;
import ar.edu.undec.level.controller.dto.MesaRequest;
import ar.edu.undec.level.controller.dto.PedidoDto;
import ar.edu.undec.level.controller.dto.Response;
import ar.edu.undec.level.storage.entity.EstadoMesa;
import ar.edu.undec.level.storage.entity.Mesa;
import ar.edu.undec.level.storage.entity.Pedido;
import ar.edu.undec.level.storage.repository.MesaRepository;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
@Service
public class MesasService {
    private static final Logger LOG = LoggerFactory.getLogger(MesaController.class);

    @Autowired
    private MesaRepository mesaRepository;

    public Response save(MesaRequest request) {
        Response response = new Response();
        Mesa entity = new Mesa();
        entity.setEstado(EstadoMesa.LIBRE);
        mesaRepository.save(entity);
        response.setData(entity);
        return response;
    }
    public Response findAll() {
        Response  response = new Response();
        try {
            List<Mesa> mesaList = mesaRepository.findAll();
            for (Mesa iterator:mesaList                 ) {
                iterator.setPedidosList(null); ;
            }
            response.setData(mesaList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response;
    }
}

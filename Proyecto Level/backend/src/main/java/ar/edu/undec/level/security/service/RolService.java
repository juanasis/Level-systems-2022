package ar.edu.undec.level.security.service;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.enums.RolNombre;
import ar.edu.undec.level.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }

    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }
}

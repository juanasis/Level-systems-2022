package ar.edu.undec.level.security.service;
import ar.edu.undec.level.security.entity.Permiso;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.repository.PermisoRepository;
import ar.edu.undec.level.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public Optional<Rol> getByRolNombre(String rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }

    public Optional<Rol> buscarPorId(Integer idRol) {
        return rolRepository.findById(idRol);
    }

    public void eliminarRolPorId(Integer idRol) {
        rolRepository.deleteById(idRol);
    }

    public List<Rol> getRoles() {
        return rolRepository.findAll();
    }

    public List<Permiso> listarTodosPermisos(){
        return permisoRepository.findAll();
    }

    public Optional<Permiso> buscarPermisoPorId(Long id) {
        return permisoRepository.findById(id);
    }

    public Permiso guardar(Permiso permiso) {
        Permiso permisoEncontrado = permisoRepository.findById(permiso.getPermisoId()).get();
        permisoEncontrado.setRoles(permiso.getRoles());
        return permisoRepository.save(permisoEncontrado);
    }
}

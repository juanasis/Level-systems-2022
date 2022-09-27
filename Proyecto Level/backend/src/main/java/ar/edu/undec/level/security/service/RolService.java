package ar.edu.undec.level.security.service;
import ar.edu.undec.level.security.entity.HistoriaUsuario;
import ar.edu.undec.level.security.entity.HistorialRol;
import ar.edu.undec.level.security.entity.Permiso;
import ar.edu.undec.level.security.entity.Rol;
import ar.edu.undec.level.security.repository.HistorialRolRepository;
import ar.edu.undec.level.security.repository.PermisoRepository;
import ar.edu.undec.level.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private HistorialRolRepository historialRolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    public Optional<Rol> getByRolNombre(String rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public Rol save(Rol rol){
        return rolRepository.save(rol);
    }

    public Optional<Rol> buscarPorId(Integer idRol) {
        return rolRepository.findById(idRol);
    }

    @Transactional
    public void eliminarRolPorId(Integer idRol) {
        historialRolRepository.eliminarHistorialDeUnRol(idRol);
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

        agregarHistorialRolConPermisosActualizados(permisoEncontrado, permiso);
        permisoEncontrado.setRoles(permiso.getRoles());

        return permisoRepository.save(permisoEncontrado);
    }

    public void crearHistoriaRol(HistorialRol historialRol){
        historialRolRepository.save(historialRol);
    }

    public List<HistorialRol> listarHistorialRol(Integer rol_id) {
        return historialRolRepository.findByRol_Id(rol_id);
    }

    private void agregarHistorialRolConPermisosActualizados(Permiso permisoEncontrado, Permiso permisoFront) {

        List<String> rolesPermisoEncontrado = cambiarRolesToString(cambiarSetToList(permisoEncontrado.getRoles()));
        List<String> rolesPermisoFront = cambiarRolesToString(cambiarSetToList(permisoFront.getRoles()));

        if(rolesPermisoEncontrado.size() > rolesPermisoFront.size()) {
            for (String rol: rolesPermisoEncontrado) {
                if(!rolesPermisoFront.contains(rol)){
                    HistorialRol historialRol = new HistorialRol();
                    Rol rolEncontrado = getByRolNombre(rol).get();
                    rolEncontrado.setFecha_actualizacion(LocalDate.now());
                    historialRol.setDetalle("Se quitó el siguiente permiso "+permisoEncontrado.getNombre() + ".");
                    historialRol.setRol(getByRolNombre(rol).get());
                    historialRolRepository.save(historialRol);
                    rolRepository.save(getByRolNombre(rol).get());
                }
            }
        }

        if(rolesPermisoFront.size() > rolesPermisoEncontrado.size()) {
            for (String rol: rolesPermisoFront) {
                if(!rolesPermisoEncontrado.contains(rol)){
                    HistorialRol historialRol = new HistorialRol();
                    Rol rolEncontrado = getByRolNombre(rol).get();
                    rolEncontrado.setFecha_actualizacion(LocalDate.now());
                    historialRol.setDetalle("Se agregó el siguiente permiso "+permisoEncontrado.getNombre() + ".");
                    historialRol.setRol(getByRolNombre(rol).get());
                    historialRolRepository.save(historialRol);
                    rolRepository.save(getByRolNombre(rol).get());
                }
            }
        }
    }

    private List<Rol> cambiarSetToList(Set<Rol> roles){
        List<Rol> rolesCambiados = new ArrayList<>();
        roles.stream().forEach(r -> rolesCambiados.add(r));
        return rolesCambiados;
    }

    public List<String> cambiarRolesToString(List<Rol> roles){
        return roles.stream().map(Rol::getRolNombre).collect(Collectors.toList());
    }
}

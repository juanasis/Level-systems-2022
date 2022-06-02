package ar.edu.undec.level.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permisoId;

    private String nombre;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"permisos","hibernateLazyInitializer", "handler"})
    @JoinTable(
            name = "permisos_roles",
            joinColumns = @JoinColumn(name = "permisoId"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Rol> roles;

    public Long getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(Long permisoId) {
        this.permisoId = permisoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}

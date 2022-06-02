package ar.edu.undec.level.security.entity;

import ar.edu.undec.level.security.enums.RolNombre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
//    @Enumerated(EnumType.STRING)
    private String rolNombre;

//    @JsonIgnore
//    @JsonIgnoreProperties({"roles","hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Permiso> permisos;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonIgnoreProperties({"roles","hibernateLazyInitializer", "handler"})
    private Set<Usuario> usuarios= new HashSet<>();

    public Rol() {
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }
    public Rol(int id, String rolNombre) {
        this.id = id;
        this.rolNombre = rolNombre;
    }

    public Rol(@NotNull String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolNombre() {
        return rolNombre;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}

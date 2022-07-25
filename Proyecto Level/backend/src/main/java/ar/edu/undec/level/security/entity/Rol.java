package ar.edu.undec.level.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<Permiso> permisos;

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

    public Set<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(Set<Permiso> permisos) {
        this.permisos = permisos;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}

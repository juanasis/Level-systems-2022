package ar.edu.undec.level.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_creacion;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fecha_actualizacion;

//    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<Permiso> permisos;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonIgnoreProperties({"roles","hibernateLazyInitializer", "handler"})
    private Set<Usuario> usuarios= new HashSet<>();

    public Rol() {
    }

    @PrePersist
    public void fechaCreacion() {
        this.fecha_creacion = LocalDate.now();
    }

    @PreUpdate
    public void fechaActualizacion() {
        this.fecha_actualizacion = LocalDate.now();
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

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDate getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(LocalDate fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}

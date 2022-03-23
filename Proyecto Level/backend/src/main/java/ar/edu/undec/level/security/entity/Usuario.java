package ar.edu.undec.level.security.entity;

import ar.edu.undec.level.storage.entity.Pedido;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    @Column(unique = true)
    private String nombreUsuario;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private String tokenPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles= new HashSet<>();
    @OneToMany(mappedBy = "mozo", cascade = CascadeType.ALL)
    private Collection<Pedido> pedidos;
    public Usuario( ) {
    }

    public Usuario(@NotNull String nombre, @NotNull String nombreUsuario, @NotNull String email, @NotNull String password) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}

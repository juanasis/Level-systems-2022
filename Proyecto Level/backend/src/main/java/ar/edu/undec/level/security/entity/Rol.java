package ar.edu.undec.level.security.entity;

import ar.edu.undec.level.security.enums.RolNombre;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
//    @Enumerated(EnumType.STRING)
    private String rolNombre;

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
}

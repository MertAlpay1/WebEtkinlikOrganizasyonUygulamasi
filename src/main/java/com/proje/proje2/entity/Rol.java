package com.proje.proje2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @NotNull

    
    private String role;

    public Rol() {
    }

    public Rol(long id, String role) {
        this.id = id;
        this.role = role;
    }

    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Rol{" + "id=" + id + ", role=" + role + '}';
    }
    
    
}

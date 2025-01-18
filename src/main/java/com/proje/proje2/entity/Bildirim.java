package com.proje.proje2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"kullanıcı_id", "etkinlik_id"})})
public class Bildirim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanıcı_id", nullable = false)
    private Kullanıcı kullanıcı;

    @ManyToOne
    @JoinColumn(name = "etkinlik_id", nullable = false)
    private Etkinlik etkinlik;

    public Bildirim() {
    }

    public Bildirim(Long id, Kullanıcı kullanıcı, Etkinlik etkinlik) {
        this.id = id;
        this.kullanıcı = kullanıcı;
        this.etkinlik = etkinlik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanıcı getKullanıcı() {
        return kullanıcı;
    }

    public void setKullanıcı(Kullanıcı kullanıcı) {
        this.kullanıcı = kullanıcı;
    }

    public Etkinlik getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(Etkinlik etkinlik) {
        this.etkinlik = etkinlik;
    }
    
    
    
}

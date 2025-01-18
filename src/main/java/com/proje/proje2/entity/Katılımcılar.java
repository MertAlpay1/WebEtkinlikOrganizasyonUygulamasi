package com.proje.proje2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "katılımcılar", uniqueConstraints = @UniqueConstraint(columnNames = {"KullanıcıID", "EtkinlikID"}))
@IdClass(KatılımcılarId.class)
public class Katılımcılar {

    @Id
    @ManyToOne
    @JoinColumn(name = "KullanıcıID", nullable = false)
    private Kullanıcı kullanıcı;

    @Id
    @ManyToOne
    @JoinColumn(name = "EtkinlikID", nullable = false)
    private Etkinlik etkinlik;

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

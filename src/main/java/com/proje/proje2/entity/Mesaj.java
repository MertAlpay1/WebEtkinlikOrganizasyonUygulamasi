
package com.proje.proje2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Mesaj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "göndericiid", nullable = false)
    private Kullanıcı kullanıcı;
    
    @ManyToOne
    @JoinColumn(name = "alıcıid", nullable = false)
    private Etkinlik etkinlik;
    @Column(nullable = false)
    private String mesaj;
    @Column(nullable = false)
    private LocalDateTime gönderimZamanı;

    public Mesaj() {
    }

    public Mesaj(long id, Kullanıcı kullanıcı, Etkinlik etkinlik, String mesaj, LocalDateTime gönderimZamanı) {
        this.id = id;
        this.kullanıcı = kullanıcı;
        this.etkinlik = etkinlik;
        this.mesaj = mesaj;
        this.gönderimZamanı = gönderimZamanı;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public LocalDateTime getGönderimZamanı() {
        return gönderimZamanı;
    }

    public void setGönderimZamanı(LocalDateTime gönderimZamanı) {
        this.gönderimZamanı = gönderimZamanı;
    }

    
}

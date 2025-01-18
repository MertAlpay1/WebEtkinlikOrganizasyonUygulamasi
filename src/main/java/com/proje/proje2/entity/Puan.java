
package com.proje.proje2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Puan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "KullanıcıID", nullable = false)
    private Kullanıcı kullanıcı;
    
    private Long puanlar; 
    
    private String tarih;

    public Puan() {
    }

    public Puan(long id, Kullanıcı kullanıcı, Long puanlar, String tarih) {
        this.id = id;
        this.kullanıcı = kullanıcı;
        this.puanlar = puanlar;
        this.tarih = tarih;
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

    public Long getPuanlar() {
        return puanlar;
    }

    public void setPuanlar(Long puanlar) {
        this.puanlar = puanlar;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
    
    
    
}

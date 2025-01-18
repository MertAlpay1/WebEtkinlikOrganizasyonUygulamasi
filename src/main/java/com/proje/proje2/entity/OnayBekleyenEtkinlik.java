package com.proje.proje2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class OnayBekleyenEtkinlik {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    private String etkinlikadı;
    
    private String açıklama;
    
    private String tarih;
    
    private String saat;
    
    private String süresi;
    
    private String konum;
    
    private String kategori;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanıcı kullanici; 

    public OnayBekleyenEtkinlik() {
    }

    public OnayBekleyenEtkinlik(long id, String etkinlikadı, String açıklama, String tarih, String saat, String süresi, String konum, String kategori, Kullanıcı kullanici) {
        this.id = id;
        this.etkinlikadı = etkinlikadı;
        this.açıklama = açıklama;
        this.tarih = tarih;
        this.saat = saat;
        this.süresi = süresi;
        this.konum = konum;
        this.kategori = kategori;
        this.kullanici = kullanici;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtkinlikadı() {
        return etkinlikadı;
    }

    public void setEtkinlikadı(String etkinlikadı) {
        this.etkinlikadı = etkinlikadı;
    }

    public String getAçıklama() {
        return açıklama;
    }

    public void setAçıklama(String açıklama) {
        this.açıklama = açıklama;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getSüresi() {
        return süresi;
    }

    public void setSüresi(String süresi) {
        this.süresi = süresi;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Kullanıcı getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanıcı kullanici) {
        this.kullanici = kullanici;
    }

    
    
    
    
}

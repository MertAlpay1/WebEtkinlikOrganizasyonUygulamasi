package com.proje.proje2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;


@Entity
public class Kullanıcı {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    
    private long id;
    @NotNull
    @Column(unique=true)
    private String kullanıcıadı;
    @NotNull
    private String şifre;
    
    private String cinsiyet;
            
    private String eposta;
    private String konum;
    
    @NotNull
    @ManyToOne
    private Rol rol;
    
    private String doğumtarihi;
    
    private String ilgialanlari;
    
    private String ad;
    private String soyAd;
    
    private String telefonnumarası;
    @Lob
    private byte[] profilfotoğrafı;

    

    

    public Kullanıcı() {
    }

    public Kullanıcı(long id, String kullanıcıadı, String şifre, String cinsiyet, String eposta, String konum, Rol rol, String doğumtarihi, String ilgialanlari, String ad, String soyAd, String telefonnumarası, byte[] profilfotoğrafı) {
        this.id = id;
        this.kullanıcıadı = kullanıcıadı;
        this.şifre = şifre;
        this.cinsiyet = cinsiyet;
        this.eposta = eposta;
        this.konum = konum;
        this.rol = rol;
        this.doğumtarihi = doğumtarihi;
        this.ilgialanlari = ilgialanlari;
        this.ad = ad;
        this.soyAd = soyAd;
        this.telefonnumarası = telefonnumarası;
        this.profilfotoğrafı = profilfotoğrafı;
    }

    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKullanıcıadı() {
        return kullanıcıadı;
    }

    public void setKullanıcıadı(String kullanıcıadı) {
        this.kullanıcıadı = kullanıcıadı;
    }

    public String getŞifre() {
        return şifre;
    }

    public void setŞifre(String şifre) {
        this.şifre = şifre;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getKonum() {
        return konum;
    }

    public void setKonum(String konum) {
        this.konum = konum;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getDoğumtarihi() {
        return doğumtarihi;
    }

    public void setDoğumtarihi(String doğumtarihi) {
        this.doğumtarihi = doğumtarihi;
    }

    public String getIlgialanlari() {
        return ilgialanlari;
    }

    public void setIlgialanlari(String ilgialanlari) {
        this.ilgialanlari = ilgialanlari;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyAd() {
        return soyAd;
    }

    public void setSoyAd(String soyAd) {
        this.soyAd = soyAd;
    }

    public String getTelefonnumarası() {
        return telefonnumarası;
    }

    public void setTelefonnumarası(String telefonnumarası) {
        this.telefonnumarası = telefonnumarası;
    }

    public byte[] getProfilfotoğrafı() {
        return profilfotoğrafı;
    }

    public void setProfilfotoğrafı(byte[] profilfotoğrafı) {
        this.profilfotoğrafı = profilfotoğrafı;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    
    
    
    
    

    

    
    
    
    
}
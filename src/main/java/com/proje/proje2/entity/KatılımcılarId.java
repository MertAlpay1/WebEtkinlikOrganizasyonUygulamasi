package com.proje.proje2.entity;

import java.io.Serializable;
import java.util.Objects;

public class KatılımcılarId implements Serializable {

    private Long kullanıcı;
    private Long etkinlik;

    public KatılımcılarId() {
    }

    public KatılımcılarId(Long kullanıcı, Long etkinlik) {
        this.kullanıcı = kullanıcı;
        this.etkinlik = etkinlik;
    }

    public Long getKullanıcı() {
        return kullanıcı;
    }

    public void setKullanıcı(Long kullanıcı) {
        this.kullanıcı = kullanıcı;
    }

    public Long getEtkinlik() {
        return etkinlik;
    }

    public void setEtkinlik(Long etkinlik) {
        this.etkinlik = etkinlik;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KatılımcılarId that = (KatılımcılarId) o;
        return Objects.equals(kullanıcı, that.kullanıcı) &&
               Objects.equals(etkinlik, that.etkinlik);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kullanıcı, etkinlik);
    }
}

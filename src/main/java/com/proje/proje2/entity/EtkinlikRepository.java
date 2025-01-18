package com.proje.proje2.entity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtkinlikRepository extends JpaRepository<Etkinlik,Long> {
    Optional<Etkinlik> findByetkinlikadı(String etkinlikadı);
    List<Etkinlik> findByKategoriIn(List<String> kategori);
    
    
}

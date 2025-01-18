
package com.proje.proje2.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KatılımcılarRepository extends JpaRepository<Katılımcılar, KatılımcılarId> {
    List<Katılımcılar> findByKullanıcıId(long Kullanıcıid);
    List<Katılımcılar> findByEtkinlikId(long EtkinlikID);
    
    
}

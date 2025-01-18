
package com.proje.proje2.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesajRepository extends JpaRepository<Mesaj,Long> {
    
    List<Mesaj> findByEtkinlikId(Long alıcıid);
    
    
}

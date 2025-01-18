
package com.proje.proje2.entity;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BildirimRepository extends JpaRepository<Bildirim,Long>{
    List<Bildirim> findByKullanıcı(Kullanıcı kullanıcı);
    @Transactional
    void deleteAllByKullanıcı(Kullanıcı kullanıcı);
}

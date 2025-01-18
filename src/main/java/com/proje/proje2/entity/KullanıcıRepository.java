package com.proje.proje2.entity;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface KullanıcıRepository extends JpaRepository<Kullanıcı,Long> {
    Kullanıcı findBykullanıcıadı(String kullanıcıadı);
    List <Kullanıcı> findByRol_id(long rol);

}

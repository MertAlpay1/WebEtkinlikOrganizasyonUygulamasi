package com.proje.proje2.Controller;

import com.proje.proje2.entity.Etkinlik;
import com.proje.proje2.entity.EtkinlikRepository;
import com.proje.proje2.entity.Katılımcılar;
import com.proje.proje2.entity.KatılımcılarRepository;
import com.proje.proje2.entity.Kullanıcı;
import com.proje.proje2.entity.KullanıcıRepository;
import com.proje.proje2.entity.OnayBekleyenEtkinlik;
import com.proje.proje2.entity.OnayBekleyenEtkinlikRepository;
import com.proje.proje2.entity.Puan;
import com.proje.proje2.entity.PuanRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminControl {
    
    @Autowired
    private KullanıcıRepository kullanıcıRepository;
    @Autowired
    private EtkinlikRepository etkinlikRepository;
    @Autowired
    private OnayBekleyenEtkinlikRepository onaybekleyenetkinkrepository;
    @Autowired
    private KatılımcılarRepository katılımcılarRepository;
    @Autowired
    private PuanRepository puanRepository;
    
    @GetMapping("/acceptactivity")
    public String AcceptActivityMenu(Model model){
        
        List<OnayBekleyenEtkinlik> etkinlikler = onaybekleyenetkinkrepository.findAll();
        model.addAttribute("OnayBekleyenEtkinlik", etkinlikler);
        return "/acceptactivity";
    }
    //aktivite eklendiğinde kullanıcıya puan ekle
    @PostMapping("/onayla/{id}")
    public String AcceptActivity(@PathVariable Long id){
        
        try {
        OnayBekleyenEtkinlik onaybekleyenetkinlik = onaybekleyenetkinkrepository.getById(id);
        
        Etkinlik etkinlik = new Etkinlik();
        
        etkinlik.setEtkinlikadı(onaybekleyenetkinlik.getEtkinlikadı());
        etkinlik.setKategori(onaybekleyenetkinlik.getKategori());
        etkinlik.setAçıklama(onaybekleyenetkinlik.getAçıklama());
        etkinlik.setTarih(onaybekleyenetkinlik.getTarih());
        etkinlik.setSaat(onaybekleyenetkinlik.getSaat());
        etkinlik.setSüresi(onaybekleyenetkinlik.getSüresi());
        etkinlik.setKonum(onaybekleyenetkinlik.getKonum());

        etkinlikRepository.save(etkinlik);
        
        Katılımcılar katılımcı=new Katılımcılar();
        katılımcı.setKullanıcı(onaybekleyenetkinlik.getKullanici());
        katılımcı.setEtkinlik(etkinlik);
        
        katılımcılarRepository.save(katılımcı);
        
        Puan puan=new Puan();
        puan.setKullanıcı(onaybekleyenetkinlik.getKullanici());
        puan.setPuanlar(15L);
        puan.setTarih(java.time.LocalDate.now().toString());
        puanRepository.save(puan);
        Puan puan2=new Puan();
        puan2.setKullanıcı(onaybekleyenetkinlik.getKullanici());
        puan2.setPuanlar(10L);
        puan2.setTarih(java.time.LocalDate.now().toString());
        puanRepository.save(puan2);
        onaybekleyenetkinkrepository.deleteById(id);

    } catch (Exception e) {
        
        
        return "/adminMenu";
    }
        
        return "/adminMenu";
    }
    @PostMapping("/reddet/{id}")
    public String RejectActivity(@PathVariable Long id){
        try{
            OnayBekleyenEtkinlik onaybekleyenetkinlik = onaybekleyenetkinkrepository.getById(id);
            onaybekleyenetkinkrepository.deleteById(id);

        }
        catch(Exception e){
            
            return "/adminMenu";
        }
        
        
        return "/adminMenu";
    }
    
    @GetMapping("/showactivity")
    public String showActivity(Model model){
        List<Etkinlik> etkinlik=etkinlikRepository.findAll();
        model.addAttribute("etkinlikler",etkinlik);
        
        return "/showactivity";
    }
    @GetMapping("/düzenle/{id}")
    public String reDirectUpdateEtkinlikMenu(@PathVariable Long id, Model model) {
    Optional<Etkinlik> etkinlik = etkinlikRepository.findById(id);
    if (etkinlik.isPresent()) {
        model.addAttribute("etkinlik", etkinlik.get());
    }
    return "/updateEtkinlik"; 
    }
    
    @PostMapping("/updateE")
    public String updateEtkinlik(
        @RequestParam("etkilikid") String id,
        @RequestParam("etkinlikadı") String etkinlikadı,
        @RequestParam("kategori") String kategori,
        @RequestParam("aciklama") String aciklama,
        @RequestParam("tarih") String tarih,
        @RequestParam("saat") String saat,
        @RequestParam("süresi") String süresi
        ) {
        
        Long etkinlikId = Long.parseLong(id);
        
         Optional<Etkinlik> mevcutEtkinlik = etkinlikRepository.findById(etkinlikId);
    
         if (mevcutEtkinlik.isPresent()) {
             Etkinlik etkinlik = mevcutEtkinlik.get();
        
             etkinlik.setEtkinlikadı(etkinlikadı);
             etkinlik.setKategori(kategori);
             etkinlik.setAçıklama(aciklama);
             etkinlik.setTarih(tarih);
             etkinlik.setSaat(saat);
             etkinlik.setSüresi(süresi);
             
        etkinlikRepository.save(etkinlik);
    }
        
        return "adminMenu";
    }
    
     @PostMapping("/sil/{id}")
    public String deleteEtkinlik(@PathVariable Long id){
        etkinlikRepository.deleteById(id);
        
        return "/adminMenu";
    }
    
    
    @GetMapping("/showusers")
    public String ShowUserMenu(Model model){
        List <Kullanıcı> kullanıcılar=kullanıcıRepository.findByRol_id(2);
        
        model.addAttribute("kullanıcılar", kullanıcılar);
        return "/showusers";
    }
    
    
    @GetMapping("/adminupdateuser/{id}")
    public String adminUserMenu(@PathVariable Long id,Model model){
        
        Optional<Kullanıcı> kullanıcı = kullanıcıRepository.findById(id);
        
        if (kullanıcı.isPresent()) {
        model.addAttribute("kullanıcı", kullanıcı.get());
       }
       return "/adminupdateuser";
    }
    
    
    @PostMapping("/adminuserupdate/{id}")
    public String updateUserAdmin(@PathVariable Long id,
            @RequestParam("ad") String ad,
            @RequestParam("soyad") String soyad,
            @RequestParam("dogumTarihi") String dogumTarihi, 
            @RequestParam("telefonNumarasi") String telefonNumarasi, 
            @RequestParam("ilgiAlanlari") String ilgiAlanlari)
    {
        Kullanıcı kullanıcı=kullanıcıRepository.getById(id);
        kullanıcı.setAd(ad);
        kullanıcı.setSoyAd(soyad);
        kullanıcı.setDoğumtarihi(dogumTarihi);
        kullanıcı.setTelefonnumarası(telefonNumarasi);
        kullanıcı.setIlgialanlari(ilgiAlanlari);
        
        kullanıcıRepository.save(kullanıcı);
        return "/adminMenu";
    }
    
    @PostMapping("/admindeleteuser/{id}")
    public String DeleteUser(@PathVariable Long id){
        kullanıcıRepository.deleteById(id);
        
       return "/adminMenu" ;
    }
    
    
    
}

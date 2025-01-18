package com.proje.proje2.Controller;

import static com.proje.proje2.Controller.PasswordUtil.hashPassword;
import com.proje.proje2.entity.Kullanıcı;
import com.proje.proje2.entity.KullanıcıRepository;
import static com.proje.proje2.Controller.PasswordUtil.verifyPassword;
import com.proje.proje2.entity.Bildirim;
import com.proje.proje2.entity.BildirimRepository;
import com.proje.proje2.entity.Etkinlik;
import com.proje.proje2.entity.EtkinlikRepository;
import com.proje.proje2.entity.Katılımcılar;
import com.proje.proje2.entity.KatılımcılarRepository;
import com.proje.proje2.entity.Mesaj;
import com.proje.proje2.entity.MesajRepository;
import com.proje.proje2.entity.OnayBekleyenEtkinlik;
import com.proje.proje2.entity.OnayBekleyenEtkinlikRepository;
import com.proje.proje2.entity.Puan;
import com.proje.proje2.entity.PuanRepository;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KullanıcıMenuControl {
    
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
    @Autowired
    private MesajRepository mesajRepository;
    @Autowired
    private BildirimRepository bildirimRepository;
    
    
    @GetMapping("/home")
    public String Menu(){
        return "home";
    }
    
    @GetMapping("/profile")
    public String showProfile(Model model,HttpSession session){
        
        Kullanıcı kullanıcı=(Kullanıcı) session.getAttribute("loggedInUser");
        
        if (kullanıcı == null) {
            return "redirect:/login";
        }
        model.addAttribute("kullanıcı", kullanıcı);
        Long puan = puanRepository.findTotalPuanByKullanıcı(kullanıcı);
        model.addAttribute("puan",puan);
        
        
        return "profile";
    }
    @GetMapping("/profile/{userId}")
    @ResponseBody
    public byte[]getPhoto(@PathVariable("userId") Long userId)throws IOException{
        Optional<Kullanıcı> kullanıcıopt = kullanıcıRepository.findById(userId);
         
        Kullanıcı kullanıcı = kullanıcıopt.get();
        byte[] photo = kullanıcı.getProfilfotoğrafı();
        if (photo != null) {
        return photo;
        }
        return new byte[0];
    }

    
    @GetMapping("/profile/update")
    public String profileUpdate(Model model,HttpSession session){
        
        Kullanıcı kullanıcı=(Kullanıcı) session.getAttribute("loggedInUser");
        
        if (kullanıcı == null) {
            return "redirect:/login";
        }
        model.addAttribute("kullanıcı", kullanıcı);
        
        return  "updateProfile";
    }
    
    
    @PostMapping("/updateP")
    public String updateProfile(@RequestParam("ad") String ad,
                                @RequestParam("soyad") String soyad,
                                @RequestParam("dogumTarihi") String dogumTarihi, 
                                @RequestParam("telefonNumarasi") String telefonNumarasi, 
                                @RequestParam("ilgiAlanlari") String ilgiAlanlari,
                                @RequestParam("sifre") String sifre,
                                @RequestParam("Esifre") String sifreDoğrulama,
                                HttpSession session
    ){
        
       Kullanıcı kullanıcı = (Kullanıcı) session.getAttribute("loggedInUser");
       
       if (kullanıcı == null) {
            return "login";
        }
        kullanıcı.setAd(ad);
        kullanıcı.setSoyAd(soyad);
        kullanıcı.setDoğumtarihi(dogumTarihi);
        kullanıcı.setTelefonnumarası(telefonNumarasi);
        kullanıcı.setIlgialanlari(ilgiAlanlari);
        
        if(verifyPassword(sifreDoğrulama,kullanıcı.getŞifre())){
            
            
            kullanıcı.setŞifre(hashPassword(sifre));
        }

        
        kullanıcıRepository.save(kullanıcı);
        session.setAttribute("loggedInUser", kullanıcı);        
        return "home";
    }
    @GetMapping("/profile/update/Photo")
    public String MapProfilePhoto(){
        
        
        return "updatePhoto";
    }
    
    @PostMapping("/updatePhoto")
    public String updateProfilePhoto(@RequestParam("profilePhoto") MultipartFile profilePhoto, HttpSession session){
        Kullanıcı kullanıcı = (Kullanıcı) session.getAttribute("loggedInUser");
        try {
            byte[] photoBytes = profilePhoto.getBytes();
            kullanıcı.setProfilfotoğrafı(photoBytes);
            kullanıcıRepository.save(kullanıcı);
            session.setAttribute("loggedInUser", kullanıcı);

            return "home"; 
        } catch (IOException e) {
            
            return "updateProfile"; 
        }
        
   }
    
    @GetMapping("/activity")
    public String ActivityMenu(){
        
        return "/activity";
    }
    
    
    @GetMapping("/createactivity")
    public String CreateActivityMenu(){
        
        return "/createactivity";
    }
    @PostMapping("/createactivity")
    public String CreateActivity(@RequestParam("etkinlikadı") String etkinlikadı,
                                 @RequestParam("kategori") String kategori,
                                 @RequestParam("açıklama") String açıklama,
                                 @RequestParam("tarih") String tarih,
                                 @RequestParam("saat") String saat,
                                 @RequestParam("süresi")String süresi,
                                 @RequestParam("latitude") String latitude,
                                 @RequestParam("longitude") String longitude,
                                 HttpSession session,
                                 Model model
            
    ){
        
        Kullanıcı kullanıcı=(Kullanıcı) session.getAttribute("loggedInUser");
        
        List<Katılımcılar> katılımcılar = katılımcılarRepository.findByKullanıcıId(kullanıcı.getId());
    List<Etkinlik> etkinlikler = katılımcılar.stream()
        .map(Katılımcılar::getEtkinlik)
        .collect(Collectors.toList());
    
    LocalDate yeniEtkinlikTarih = LocalDate.parse(tarih);
    LocalTime yeniEtkinlikSaat = LocalTime.parse(saat);
    LocalTime yeniEtkinlikBitişSaat = yeniEtkinlikSaat.plusMinutes(Long.parseLong(süresi));

    for (Etkinlik mevcutEtkinlik : etkinlikler) {
        LocalDate mevcutEtkinlikTarih = LocalDate.parse(mevcutEtkinlik.getTarih());
        LocalTime mevcutEtkinlikSaat = LocalTime.parse(mevcutEtkinlik.getSaat());
        LocalTime mevcutEtkinlikBitişSaat = mevcutEtkinlikSaat.plusMinutes(Long.parseLong(mevcutEtkinlik.getSüresi()));
        
        if (yeniEtkinlikTarih.equals(mevcutEtkinlikTarih)) {
            if ((yeniEtkinlikSaat.isBefore(mevcutEtkinlikBitişSaat) && yeniEtkinlikSaat.isAfter(mevcutEtkinlikSaat)) ||
                (yeniEtkinlikBitişSaat.isAfter(mevcutEtkinlikSaat) && yeniEtkinlikBitişSaat.isBefore(mevcutEtkinlikBitişSaat)) ||
                (yeniEtkinlikSaat.isBefore(mevcutEtkinlikSaat) && yeniEtkinlikBitişSaat.isAfter(mevcutEtkinlikSaat))) {
                
                model.addAttribute("error","Etkinlik saatleri çakışıyor. "+mevcutEtkinlik.getTarih()+" tarihinde "+mevcutEtkinlik.getSaat()+" saatinde "
                         +mevcutEtkinlik.getEtkinlikadı()+ " etkinliğiniz bulunmakta!");
                return "/activity"; 
            }
        }
    }
        
        
        
        
        Optional<Etkinlik> existingEvent = etkinlikRepository.findByetkinlikadı(etkinlikadı);

        if (existingEvent.isPresent()) {
            model.addAttribute("error", "Etkinlik adı zaten var. Lütfen farklı bir ad girin.");
            return "/createactivity"; 
        }
        
        String konum=""+latitude+","+""+longitude;
        
        OnayBekleyenEtkinlik etkinlik = new OnayBekleyenEtkinlik();
        
        etkinlik.setEtkinlikadı(etkinlikadı);
        etkinlik.setKategori(kategori);
        etkinlik.setAçıklama(açıklama);
        etkinlik.setTarih(tarih);
        etkinlik.setSaat(saat);
        etkinlik.setSüresi(süresi);
        etkinlik.setKonum(konum);
        etkinlik.setKullanici(kullanıcı); 

        onaybekleyenetkinkrepository.save(etkinlik);
        
        return "/activity";
    }

    @GetMapping("/activeactivity")
    public String KullanıcıAktiviteleri(HttpSession session,Model model){
         Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
         long id=kullanıcı.getId();
         List<Katılımcılar> katılımcılar = katılımcılarRepository.findByKullanıcıId(id);
         List<Etkinlik> etkinlikler = katılımcılar.stream()
            .map(Katılımcılar::getEtkinlik)  
            .collect(Collectors.toList());
        
        model.addAttribute("etkinlikler",etkinlikler);
        return "/activeactivity";
    }
    @GetMapping("/ShowactivityonMap/{id}")
    public String ShowOnMap(@PathVariable Long id,Model model){
        
        Etkinlik etkinlik=etkinlikRepository.getById(id);
        String[] kordinat = etkinlik.getKonum().split(",");
        double latitude = Double.parseDouble(kordinat[0]);
        double longitude = Double.parseDouble(kordinat[1]);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        model.addAttribute("tarih", etkinlik.getTarih());
        model.addAttribute("saat", etkinlik.getSaat());
        model.addAttribute("açıklama", etkinlik.getAçıklama());
        return "/ShowactivityonMap";
    }
    @GetMapping("/ActivityRoute/{id}")
    public String ActivityRoute(@PathVariable Long id,Model model){
        Etkinlik etkinlik=etkinlikRepository.getById(id);
        String[] kordinat = etkinlik.getKonum().split(",");
        double latitude = Double.parseDouble(kordinat[0]);
        double longitude = Double.parseDouble(kordinat[1]);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        
        return "/ActivityRoute";
    }
    @GetMapping("/findactivitymenu")
    public String FindActivityMenu(){
        
        
        return "/findactivitymenu";
    }
    @GetMapping("/normalEtkinlik")
    public String FindNormalEtkinlik(HttpSession session,Model model){
        
        List<Etkinlik> etkinlikler = etkinlikRepository.findAll();
        model.addAttribute("etkinlikler",etkinlikler);
        return "/findactivity";
    }
    
    @GetMapping("/ilgialanetkinlik")
    public String FindActivitybyilgiAlan(HttpSession session,Model model){
        Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
        String ilgialan[]=kullanıcı.getIlgialanlari().split(",");
        List<String>ilgiAlanlari=Arrays.asList(ilgialan);
        
        List<Etkinlik> etkinlikler = etkinlikRepository.findByKategoriIn(ilgiAlanlari);
        model.addAttribute("etkinlikler",etkinlikler);
        return "/findactivity";
    }
    @GetMapping("/katılımgeçmişetkinlik")
    public String FindActivitybyUserilgiAlan(HttpSession session,Model model){
        Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
        List<Katılımcılar> katılımcılar = katılımcılarRepository.findByKullanıcıId(kullanıcı.getId());
        List<Etkinlik> kullanıcıetkinlikleri = katılımcılar.stream()
            .map(Katılımcılar::getEtkinlik)  
            .collect(Collectors.toList());
        List<String> kategoriler = kullanıcıetkinlikleri.stream()
        .map(Etkinlik::getKategori) 
        .distinct()               
        .collect(Collectors.toList());
        
        List<Etkinlik> etkinlikler=etkinlikRepository.findByKategoriIn(kategoriler);
        
        model.addAttribute("etkinlikler",etkinlikler);
        return "/findactivity";
    }
    @GetMapping("/konumuzaklıketkinlik")
    public String FindActivitybyLocation(HttpSession session,Model model){
        Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
        
        String[] kullanıcıkonum = kullanıcı.getKonum().split(",");
        double kullanıcıLatitude = Double.parseDouble(kullanıcıkonum[0]);
        double kullanıcıLongitude = Double.parseDouble(kullanıcıkonum[1]);
        
        List<EtkinlikMesafe> etkinlikMesafe= new ArrayList<>();

        
        List<Etkinlik> etkinlikler=etkinlikRepository.findAll();
        
        for(Etkinlik etkinlik:etkinlikler){
        String[] etkinlikkonum = etkinlik.getKonum().split(",");
        double etkinlikLatitude = Double.parseDouble(etkinlikkonum[0]);
        double etkinlikLongitude = Double.parseDouble(etkinlikkonum[1]);
           double mesafe = Math.sqrt(
            Math.pow(kullanıcıLatitude - etkinlikLatitude, 2) +
            Math.pow(kullanıcıLongitude - etkinlikLongitude, 2)
        ); 
            etkinlikMesafe.add(new EtkinlikMesafe(etkinlik, mesafe));
        }
        
        
        etkinlikMesafe.sort(Comparator.comparingDouble(EtkinlikMesafe::getMesafe));

        
        model.addAttribute("etkinlikler",etkinlikMesafe);
        return "/findactivitymesafe";
    }
    
    @PostMapping("/findactivity/{id}")
    public String addActivityilgiAlan(@PathVariable Long id,HttpSession session,Model model){
        Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
        List<Katılımcılar> katılımcılar = katılımcılarRepository.findByKullanıcıId(kullanıcı.getId());
        List<Etkinlik> etkinlikler = katılımcılar.stream()
            .map(Katılımcılar::getEtkinlik)  
            .collect(Collectors.toList());
        
        
        Optional<Etkinlik> etkinlik=etkinlikRepository.findById(id);
        
        
        
        
        Etkinlik yeniEtkinlik = etkinlik.get();
        int yeniEtkinlikId=(int)yeniEtkinlik.getId();
        LocalDate yeniEtkinlikTarih = LocalDate.parse(yeniEtkinlik.getTarih());
        LocalTime  yeniEtkinlikSaat=LocalTime.parse(yeniEtkinlik.getSaat());
        LocalTime yeniEtkinlikBitişSaat=yeniEtkinlikSaat.plusMinutes(Long.parseLong(yeniEtkinlik.getSüresi())) ;
                
        for (Etkinlik mevcutEtkinlik : etkinlikler) {
           
            int mevcutEtkinlikId=(int)mevcutEtkinlik.getId();
            LocalDate mevcutEtkinlikTarih=LocalDate.parse(mevcutEtkinlik.getTarih());
            LocalTime mevcutEtkinlikSaat=LocalTime.parse(mevcutEtkinlik.getSaat());
            LocalTime mevcutEtkinlikBitişSaat=mevcutEtkinlikSaat.plusMinutes(Long.parseLong(mevcutEtkinlik.getSüresi()));
            if(yeniEtkinlikId==mevcutEtkinlikId){
                model.addAttribute("error", "Bu Etkinliğe Aktif Katılımınız Bulunmaktadır .");
                 return "/findactivitymenu"; 
            }
            
            if(yeniEtkinlikTarih.equals(mevcutEtkinlikTarih)){
                
                if ((yeniEtkinlikSaat.isBefore(mevcutEtkinlikBitişSaat) && yeniEtkinlikSaat.isAfter(mevcutEtkinlikSaat)) ||
                (yeniEtkinlikBitişSaat.isAfter(mevcutEtkinlikSaat) && yeniEtkinlikBitişSaat.isBefore(mevcutEtkinlikBitişSaat)) ||
                (yeniEtkinlikSaat.isBefore(mevcutEtkinlikSaat) && yeniEtkinlikBitişSaat.isAfter(mevcutEtkinlikSaat))) {
                
                 model.addAttribute("error","Etkinlik saatleri çakışıyor. "+mevcutEtkinlik.getTarih()+" tarihinde "+mevcutEtkinlik.getSaat()+" saatinde "
                         +mevcutEtkinlik.getEtkinlikadı()+ " etkinliğiniz bulunmakta!");
                    
                return "/findactivitymenu"; 
            } 
            }
            
        }
        
        Katılımcılar yeniKatılımcı = new Katılımcılar();
        yeniKatılımcı.setKullanıcı(kullanıcı);
        yeniKatılımcı.setEtkinlik(yeniEtkinlik);
        katılımcılarRepository.save(yeniKatılımcı);
        Puan puan=new Puan();
        puan.setKullanıcı(kullanıcı);
        puan.setPuanlar(10L);
        puan.setTarih(java.time.LocalDate.now().toString());
        puanRepository.save(puan);
        return "/findactivitymenu";
    }
    
    
    
    @GetMapping("/messageMenu")
    public String MessageMenu(HttpSession session,Model model){
        Kullanıcı kullanıcı=(Kullanıcı)session.getAttribute("loggedInUser");
        List<Katılımcılar> katılım=katılımcılarRepository.findByKullanıcıId(kullanıcı.getId());
        List<Etkinlik> etkinlikler=katılım.stream()
                .map(Katılımcılar::getEtkinlik)
                .collect(Collectors.toList());
        
        model.addAttribute("etkinlikler",etkinlikler);
        return "/messageMenu";
    }
    @GetMapping("/messages/{id}")
    public String Messages(@PathVariable Long id ,Model model){
        
        List<Mesaj> mesajlar = mesajRepository.findByEtkinlikId(id);
        model.addAttribute("mesajlar", mesajlar);
        model.addAttribute("etkinlikId",id);
        return "/messages";
    }
    @PostMapping("/sendmessage")
    public String sendMessage(@RequestParam String message, 
                          @RequestParam Long etkinlikId, 
                          HttpSession session,
                          Model model) {
    Kullanıcı kullanıcı = (Kullanıcı) session.getAttribute("loggedInUser");
    
    Optional<Etkinlik> etkinlikOptional = etkinlikRepository.findById(etkinlikId);
    if (etkinlikOptional.isPresent()) {
        Etkinlik etkinlik = etkinlikOptional.get();

        Mesaj mesaj = new Mesaj();
        mesaj.setMesaj(message);
        mesaj.setKullanıcı(kullanıcı);
        mesaj.setEtkinlik(etkinlik);
        mesaj.setGönderimZamanı(java.time.LocalDateTime.now());
        mesajRepository.save(mesaj);
    }
     List<Mesaj> mesajlar = mesajRepository.findByEtkinlikId(etkinlikId);
     model.addAttribute("mesajlar", mesajlar);
     model.addAttribute("etkinlikId",etkinlikId);

     

     List<Katılımcılar> katılımcı =katılımcılarRepository.findByEtkinlikId(etkinlikId);
     List<Kullanıcı> kullanıcılar=katılımcı.stream()
             .map(Katılımcılar::getKullanıcı)
             .collect(Collectors.toList());
     
     Etkinlik etkinlik=etkinlikRepository.getById(etkinlikId);
     
     
     for (Kullanıcı kullanıcıBildirim : kullanıcılar) {
            if (kullanıcıBildirim.getId()!=kullanıcı.getId()) {
                try{
                Bildirim bildirim = new Bildirim();
                bildirim.setKullanıcı(kullanıcıBildirim);
                bildirim.setEtkinlik(etkinlik);
                bildirimRepository.save(bildirim);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    
     
     
      return "/messages";
    }
    
    
    
    
    
    
    public class EtkinlikMesafe {
    private Etkinlik etkinlik;
    private double mesafe;

        public EtkinlikMesafe() {
        }

        public EtkinlikMesafe(Etkinlik etkinlik, double mesafe) {
            this.etkinlik = etkinlik;
            this.mesafe = mesafe;
        }

        public Etkinlik getEtkinlik() {
            return etkinlik;
        }

        public void setEtkinlik(Etkinlik etkinlik) {
            this.etkinlik = etkinlik;
        }

        public double getMesafe() {
            return mesafe;
        }

        public void setMesafe(double mesafe) {
            this.mesafe = mesafe;
        }
    
    
    
    }
    
}

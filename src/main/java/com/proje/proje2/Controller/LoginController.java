package com.proje.proje2.Controller;

import com.proje.proje2.entity.Bildirim;
import com.proje.proje2.entity.BildirimRepository;
import com.proje.proje2.entity.Kullanıcı;
import com.proje.proje2.entity.KullanıcıRepository;
import com.proje.proje2.entity.Puan;
import com.proje.proje2.entity.PuanRepository;
import com.proje.proje2.entity.Rol;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class LoginController {

    @Autowired
    private KullanıcıRepository kullanıcıRepository;
    @Autowired
    private PuanRepository puanRepository;
    @Autowired
    private BildirimRepository bildirimRepository;
    
    @GetMapping({"/", "/login"})
    public String showLoginForm() {
        return "login"; 
    }
    
    
    

     //Redirect için getMapping gerekli
     @PostMapping("/login")
     public String Login(String username,String password,Model model,HttpSession session){
         
          Kullanıcı kullanıcı = kullanıcıRepository.findBykullanıcıadı(username);

          
        if (kullanıcı != null && PasswordUtil.verifyPassword(password,kullanıcı.getŞifre())) {
            
            session.setAttribute("loggedInUser", kullanıcı);
            
            if (kullanıcı.getRol().getRole().equals("Admin")) {
                return "adminMenu";
            } else {
                List<Bildirim> bildirimler = bildirimRepository.findByKullanıcı(kullanıcı);
                model.addAttribute("bildirimler", bildirimler);
               //  bildirimleri silme ekle
               
               bildirimRepository.deleteAllByKullanıcı(kullanıcı);

               
                return "home";
            }
        } 
            model.addAttribute("error", "Geçersiz kullanıcı adı veya şifre.");
            return "login";
        
         
     } 
     @GetMapping("/register")
     public String Register(){
         
         return "register";
     }
     //kullanıcıya katılım puanı ekle
     @PostMapping("/register")
     public String RegisterSave(
            @RequestParam("ad") String ad,
            @RequestParam("soyad")String soyad,
            @RequestParam("cinsiyet") String cinsiyet,
            @RequestParam("kullanıcıadı") String kullanıcıadı,
            @RequestParam("şifre") String şifre,
            @RequestParam("eposta")String eposta,
            @RequestParam("telefonnumarası")String telefonnumarası,
            @RequestParam("doğumtarihi") String doğumtarihi,
            @RequestParam("ilgialanlari") String ilgialanlari,
            @RequestParam("profilfotoğrafı") MultipartFile profilfotoğrafı,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            Model model,
            HttpSession session
            
     ){
         
         String konum=""+latitude+","+""+longitude;
         Rol rol=new Rol(2,"Kullanıcı");
         Kullanıcı kullanıcı = new Kullanıcı();
         kullanıcı.setAd(ad);
         kullanıcı.setSoyAd(soyad);
         kullanıcı.setCinsiyet(cinsiyet);
         kullanıcı.setKullanıcıadı(kullanıcıadı);
         kullanıcı.setŞifre(PasswordUtil.hashPassword(şifre)); 
         kullanıcı.setEposta(eposta);
         kullanıcı.setTelefonnumarası(telefonnumarası);
         kullanıcı.setDoğumtarihi(doğumtarihi);
         kullanıcı.setIlgialanlari(ilgialanlari);
         kullanıcı.setRol(rol);
         kullanıcı.setKonum(konum);
         
         if (!profilfotoğrafı.isEmpty()) {
             try {
                 kullanıcı.setProfilfotoğrafı(profilfotoğrafı.getBytes());
             } catch (IOException ex) {
                 model.addAttribute("error", "Kayıt sırasında bir hata oluştu.");
                 return "register";
             }
        }
         
         try {
        kullanıcıRepository.save(kullanıcı);
        Kullanıcı Kayıtkullanıcı=kullanıcıRepository.findBykullanıcıadı(kullanıcıadı);
        System.out.println("Kaydedilen Kullanıcı ID: " + Kayıtkullanıcı.getId());
        Puan puan=new Puan();
        puan.setKullanıcı(Kayıtkullanıcı);
        long puandeger=20;
        puan.setPuanlar(puandeger); 
        puan.setTarih(java.time.LocalDate.now().toString());
        puanRepository.save(puan);
        
        
       } catch (Exception ex) {
            model.addAttribute("error", "Bu kullanıcı adı veya e-posta zaten kayıtlı.");
             System.out.println(ex);
        return "register";
    }
         
         session.setAttribute("loggedInUser", kullanıcı);
         
         return "home";
     }
         
     @GetMapping("/resetpassword")
     public String ResetMenu(){
            
         return "/resetpassword";
     }
     @PostMapping("/resetpassword")
     public String Reset(@RequestParam String kullanıcıadı,
                    @RequestParam String email,
                    @RequestParam String newPassword,
                    @RequestParam String confirmPassword,
                    Model model) {
    Kullanıcı kullanıcı = kullanıcıRepository.findBykullanıcıadı(kullanıcıadı);
    
    if (kullanıcı == null || !kullanıcı.getEposta().equals(email)) {
        model.addAttribute("error", "Kullanıcı adı veya e-posta yanlış!");
        return "login";
    }

    if (!newPassword.equals(confirmPassword)) {
        model.addAttribute("error", "Şifreler eşleşmiyor!");
        return "login";
    }

    kullanıcı.setŞifre(PasswordUtil.hashPassword(newPassword)); 
    kullanıcıRepository.save(kullanıcı);

    return "login";
 }
    
    
    
     
     
    
     @GetMapping("/logout")
     public String logout(HttpSession session) {
     session.invalidate();
     return "redirect:/"; 
     }
    
}

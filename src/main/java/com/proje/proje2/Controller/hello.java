package com.proje.proje2.Controller;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class hello {
    
    @GetMapping(path= "/hello")
    public String sayHello(){
        return "Hello World";
    }
    

}

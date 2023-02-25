package ro.itschool.controller.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

    @RequestMapping(value = {"/logout"})
    public String logout(){
        return "welcome";
    }
}

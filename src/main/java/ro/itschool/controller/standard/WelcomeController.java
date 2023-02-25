package ro.itschool.controller.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.util.Constants;

@Controller
public class WelcomeController {

  @RequestMapping(value = "/welcome")
  public String welcome() {
    return "welcome";
  }

  public String goToLogin (){
    return Constants.REDIRECT_TO_LOGIN;
  }

}
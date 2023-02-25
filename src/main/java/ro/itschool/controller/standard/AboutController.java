package ro.itschool.controller.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AboutController {

  @RequestMapping(value = "/about")
  public String welcome() {
    return "about";
  }

}
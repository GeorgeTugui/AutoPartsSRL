package ro.itschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductSelectController {

  @RequestMapping(value = "/productselect")
  public String welcome() {
    return "productselect";
  }
}
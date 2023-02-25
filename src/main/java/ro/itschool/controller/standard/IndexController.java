package ro.itschool.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.service.UserService;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("users", userService.findAll());
        return "index";
    }
}
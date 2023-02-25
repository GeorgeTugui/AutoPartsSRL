package ro.itschool.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.util.Constants;

@Controller
public class LoginController {

    @RequestMapping(value = {"/login"})
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return Constants.REDIRECT_TO_PRODUCTSELECT;
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("login-error", true);
        return "login-error";
    }

}


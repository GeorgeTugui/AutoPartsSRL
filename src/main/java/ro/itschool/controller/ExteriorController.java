package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Exterior;
import ro.itschool.entity.MyUser;
import ro.itschool.repository.ExteriorRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;
import java.util.Optional;

@Controller
@RequestMapping(value = "/exterior")
public class ExteriorController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ExteriorRepository exteriorRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/all"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("exteriors", exteriorRepository.findByDeletedIsFalse());
        return "exteriors";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteExterior(@PathVariable Integer id) {
        shoppingCartService.deleteExteriorByIdFromShoppingCart(id);
        exteriorRepository.deleteById(id);
        return Constants.REDIRECT_TO_EXTERIORS;
    }

    @RequestMapping(value = "/add/{id}")
    public String addExteriorToShoppingCart(@PathVariable Integer id) {
        Optional<Exterior> optionalExterior = exteriorRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalExterior.ifPresent(exterior -> {
            userByUserName.getShoppingCart().addExteriorToShoppingCart(exterior);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_EXTERIORS;
    }

    @GetMapping(value = "/add-new")
    public String addExterior(Model model) {
        model.addAttribute("exterior", new Exterior());
        return "add-exterior";
    }

    @PostMapping(value = "/add-new")
    public String addExterior(@ModelAttribute("exterior") @RequestBody Exterior exterior) {
        exterior.setDeleted(false);
        exteriorRepository.save(exterior);
        return Constants.REDIRECT_TO_EXTERIORS;
    }

}


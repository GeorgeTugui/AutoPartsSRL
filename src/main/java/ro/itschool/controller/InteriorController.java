package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Interior;
import ro.itschool.entity.MyUser;
import ro.itschool.repository.InteriorRepository;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;
import java.util.Optional;

@Controller
@RequestMapping(value = "/interior")
public class InteriorController {

    @Autowired
    private InteriorRepository interiorRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/all"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("interiors", interiorRepository.findByDeletedIsFalse());
        return "interiors";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteInterior(@PathVariable Integer id) {
        shoppingCartService.deleteInteriorByIdFromShoppingCart(id);
        interiorRepository.deleteById(id);
        return Constants.REDIRECT_TO_INTERIORS;
    }

    @RequestMapping(value = "/add/{id}")
    public String addInteriorToShoppingCart(@PathVariable Integer id) {
        Optional<Interior> optionalInterior = interiorRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalInterior.ifPresent(interior -> {
            userByUserName.getShoppingCart().addInteriorToShoppingCart(interior);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_INTERIORS;
    }

    @GetMapping(value = "/add-new")
    public String addInterior(Model model) {
        model.addAttribute("interior", new Interior());
        return "add-interior";
    }

    @PostMapping(value = "/add-new")
    public String addInterior(@ModelAttribute("interior") @RequestBody Interior interior) {
        interior.setDeleted(false);
        interiorRepository.save(interior);
        return Constants.REDIRECT_TO_INTERIORS;
    }

}


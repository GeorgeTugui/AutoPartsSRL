package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Detailing;
import ro.itschool.entity.MyUser;
import ro.itschool.repository.DetailingRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;
import java.util.Optional;

@Controller
@RequestMapping(value = "/detailing")
public class DetailingController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private DetailingRepository detailingRepository;


    @RequestMapping(value = {"/all"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("detailings", detailingRepository.findByDeletedIsFalse());
        return "detailings";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteDetailing(@PathVariable Integer id) {
        shoppingCartService.deleteDetailingByIdFromShoppingCart(id);
        detailingRepository.deleteById(id);
        return Constants.REDIRECT_TO_DETAILINGS;
    }

    @RequestMapping(value = "/add/{id}")
    public String addDetailingToShoppingCart(@PathVariable Integer id) {
        Optional<Detailing> optionalDetailing = detailingRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalDetailing.ifPresent(detailing -> {
            userByUserName.getShoppingCart().addDetailingToShoppingCart(detailing);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_DETAILINGS;
    }

    @GetMapping(value = "/add-new")
    public String addDetailing(Model model) {
        model.addAttribute("detailing", new Detailing());
        return "add-detailing";
    }

    @PostMapping(value = "/add-new")
    public String addDetailing(@ModelAttribute("detailing") @RequestBody Detailing detailing) {
        detailing.setDeleted(false);
        detailingRepository.save(detailing);
        return Constants.REDIRECT_TO_DETAILINGS;
    }

}

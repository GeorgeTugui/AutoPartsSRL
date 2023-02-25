package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.*;
import ro.itschool.repository.*;
import ro.itschool.service.FavoritesService;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;

import java.util.Optional;

@Controller
@RequestMapping(value = "/favorites")
public class FavoritesController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private InteriorRepository interiorRepository;

    @Autowired
    private ExteriorRepository exteriorRepository;

    @Autowired
    private DetailingRepository detailingRepository;

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping
    public String getFavoritesForPrincipal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        model.addAttribute("products", userByUserName.getFavorites().getProducts());

        return "favorites";
    }

    @RequestMapping(value = "/product/remove/{productId}")
    public String removeProductFromFavorites(@PathVariable Integer productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        userByUserName.getFavorites().getProducts().removeIf(product -> product.getId().equals(productId));
        userService.updateUser(userByUserName);

        return Constants.REDIRECT_TO_FAVORITES;
    }

    @RequestMapping(value = "/add-fav/{id}")
    public String addProductToFavorites(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalProduct.ifPresent(product -> {
            userByUserName.getFavorites().addProductToFavorites(product);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_PRODUCTS;
    }

    @RequestMapping(value = "/add-favd/{id}")
    public String addDetailingToFavorites(@PathVariable Integer id) {
        Optional<Detailing> optionalDetailing = detailingRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalDetailing.ifPresent(detailing -> {
            userByUserName.getFavorites().addDetailingToFavorites(detailing);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_DETAILINGS;
    }

    @RequestMapping(value = "/add-fave/{id}")
    public String addExteriorToFavorites(@PathVariable Integer id) {
        Optional<Exterior> optionalExterior = exteriorRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalExterior.ifPresent(exterior -> {
            userByUserName.getFavorites().addExteriorToFavorites(exterior);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_EXTERIORS;
    }

    @RequestMapping(value = "/add-favi/{id}")
    public String addInteriorToFavorites(@PathVariable Integer id) {
        Optional<Interior> optionalInterior = interiorRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalInterior.ifPresent(interior -> {
            userByUserName.getFavorites().addInteriorToFavorites(interior);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_INTERIORS;
    }

    @RequestMapping(value = "/to-orderf")
    public String convertToOrder(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser user = userService.findUserByUserName(currentPrincipalName);
        Order order = orderRepository.save(favoritesService.convertFavoritesToOrder(user.getFavorites()));
        user.getFavorites().getProducts().clear();
        userService.updateUser(user);
        model.addAttribute("order", order);

        return Constants.REDIRECT_TO_ORDERS;
    }

}

//    @RequestMapping(value = "/to-cart")
//    public String convertToCart(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = auth.getName();
//        MyUser user = userService.findUserByUserName(currentPrincipalName);
//        ShoppingCart shoppingCart = shoppingCartRepository.save(favoritesService.convertFavoritesToCart(user.getFavorites()));
//        user.getFavorites().getProducts().clear();
//        userService.updateUser(user);
//        model.addAttribute("shoppingCart", shoppingCart);
//
//        return Constants.REDIRECT_TO_SHOPPINGCART;
//    }

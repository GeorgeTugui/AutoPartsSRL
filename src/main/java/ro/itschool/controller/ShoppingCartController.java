package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Order;
import ro.itschool.entity.Product;
import ro.itschool.repository.*;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;

import java.util.Optional;

@Controller
@RequestMapping(value = "/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DetailingRepository detailingRepository;

    @Autowired
    private InteriorRepository interiorRepository;

    @Autowired
    private ExteriorRepository exteriorRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private FavoritesRepository favoritesRepository;

    @RequestMapping(value = "/to-order")
    public String convertToOrder(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser user = userService.findUserByUserName(currentPrincipalName);
        Order order = orderRepository.save(shoppingCartService.convertShoppingCartToOrder(user.getShoppingCart()));
        user.getShoppingCart().getProducts().clear();
        userService.updateUser(user);
        model.addAttribute("order", order);

        return Constants.REDIRECT_TO_ORDERS;
    }

    @RequestMapping
    public String getShoppingCartForPrincipal(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        model.addAttribute("products", userByUserName.getShoppingCart().getProducts());

        return "shopping-cart";
    }

    @RequestMapping(value = "/product/remove/{productId}")
    public String removeProductFromShoppingCart(@PathVariable Integer productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);

        Optional<Product> optionalProduct = productRepository.findById(productId);
        userByUserName.getShoppingCart().getProducts().removeIf(product -> product.getId().equals(productId));
        userService.updateUser(userByUserName);

        return Constants.REDIRECT_TO_SHOPPINGCART;
    }

}

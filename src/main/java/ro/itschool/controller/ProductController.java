package ro.itschool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.MyUser;
import ro.itschool.entity.Product;
import ro.itschool.repository.DetailingRepository;
import ro.itschool.repository.ExteriorRepository;
import ro.itschool.repository.InteriorRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.ShoppingCartService;
import ro.itschool.service.UserService;
import ro.itschool.util.Constants;

import java.util.Optional;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

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

    @RequestMapping(value = {"/all"})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        shoppingCartService.deleteProductByIdFromShoppingCart(id);
        productRepository.deleteById(id);
        return Constants.REDIRECT_TO_PRODUCTS;
    }

    @RequestMapping(value = "/add/{id}")
    public String addProductToShoppingCart(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        MyUser userByUserName = userService.findUserByUserName(currentPrincipalName);
        optionalProduct.ifPresent(product -> {
            userByUserName.getShoppingCart().addProductToShoppingCart(product);
            userService.updateUser(userByUserName);
        });
        return Constants.REDIRECT_TO_PRODUCTS;
    }

    @GetMapping(value = "/add-new")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping(value = "/add-new")
    public String addProduct(@ModelAttribute("product") @RequestBody Product product) {
        product.setDeleted(false);
        productRepository.save(product);
        return Constants.REDIRECT_TO_PRODUCTS;
    }

}


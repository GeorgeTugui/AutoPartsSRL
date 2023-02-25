package ro.itschool.controller.standard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ro.itschool.entity.Product;
import ro.itschool.repository.DetailingRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.UserService;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DetailingRepository detailingRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search-product")
    public String search() {
        return "search-product";
    }

    @GetMapping("/search-results")
    public String searchProduct(@RequestParam(value = "searchText", required = false, defaultValue = "") String searchText, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Product> searchResults = productRepository.findByNameContainingIgnoreCase(searchText);
        model.addAttribute(searchResults.stream().filter(product -> product.getName().contains(searchText)).collect(Collectors.toList()));
        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }

}

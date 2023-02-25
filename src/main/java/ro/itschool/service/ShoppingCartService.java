package ro.itschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Order;
import ro.itschool.entity.ShoppingCart;
import ro.itschool.repository.ShoppingCartRepository;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public Optional<ShoppingCart> findById(Integer id) {
        return shoppingCartRepository.findById(id);
    }

    public Order convertShoppingCartToOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.getProducts().addAll(shoppingCart.getProducts());
        order.setOrderDate(LocalDateTime.now());
        order.setUser(shoppingCart.getUser());
        return order;
    }

    public ShoppingCart update(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart save(ShoppingCart sc) {
        return shoppingCartRepository.save(sc);
    }

    public void deleteProductByIdFromShoppingCart(Integer productId) {
        shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getProducts().removeIf(product -> product.getId().equals(productId)))
                .toList();
    }

    public void deleteDetailingByIdFromShoppingCart(Integer detailingId) {
        shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getDetailings().removeIf(detailing -> detailing.getId().equals(detailingId)))
                .toList();
    }

    public void deleteInteriorByIdFromShoppingCart(Integer interiorId) {
        shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getInteriors().removeIf(interior -> interior.getId().equals(interiorId)))
                .toList();
    }

    public void deleteExteriorByIdFromShoppingCart(Integer exteriorId) {
        shoppingCartRepository.findAll().stream()
                .filter(cart -> cart.getExteriors().removeIf(exterior -> exterior.getId().equals(exteriorId)))
                .toList();
    }

}

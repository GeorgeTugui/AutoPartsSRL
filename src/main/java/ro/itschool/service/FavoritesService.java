package ro.itschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Favorites;
import ro.itschool.entity.Order;
import ro.itschool.entity.ShoppingCart;
import ro.itschool.repository.FavoritesRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;

    public Optional<Favorites> findById(Integer id) {
        return favoritesRepository.findById(id);
    }

    public Favorites update(Favorites favorites) {
        return favoritesRepository.save(favorites);
    }

    public Favorites save(Favorites fv) {
        return favoritesRepository.save(fv);
    }

    public void deleteProductByIdFromFavorites(Integer productId) {
        favoritesRepository.findAll().stream()
                .filter(favorites -> favorites.getProducts().removeIf(product -> product.getId().equals(productId)))
                .toList();
    }

    public void deleteDetailingByIdFromFavorites(Integer detailingId) {
        favoritesRepository.findAll().stream()
                .filter(favorites -> favorites.getDetailings().removeIf(detailing -> detailing.getId().equals(detailingId)))
                .toList();
    }

    public void deleteInteriorByIdFromFavorites(Integer interiorId) {
        favoritesRepository.findAll().stream()
                .filter(favorites -> favorites.getInteriors().removeIf(interior -> interior.getId().equals(interiorId)))
                .toList();
    }

    public void deleteExteriorByIdFromFavorites(Integer exteriorId) {
        favoritesRepository.findAll().stream()
                .filter(favorites -> favorites.getExteriors().removeIf(exterior -> exterior.getId().equals(exteriorId)))
                .toList();
    }

    public Order convertFavoritesToOrder(Favorites favorites) {
        Order order = new Order();
        order.getProducts().addAll(favorites.getProducts());
        order.setOrderDate(LocalDateTime.now());
        order.setUser(favorites.getUser());
        return order;
    }

//    public ShoppingCart convertFavoritesToCart(Favorites favorites) {
//        ShoppingCart shoppingCart = new ShoppingCart();
//        shoppingCart.getProducts().addAll(favorites.getProducts());
//        shoppingCart.setOrderDate(LocalDateTime.now());
//        shoppingCart.setUser(favorites.getUser());
//        return shoppingCart;
//    }
}

package ro.itschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Order;
import ro.itschool.entity.Product;
import ro.itschool.repository.FavoritesRepository;
import ro.itschool.repository.OrderRepository;
import ro.itschool.repository.ShoppingCartRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ShoppingCartRepository shoppingCartRepository;

    private final FavoritesRepository favoritesRepository;
    private final OrderRepository orderRepository;

    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }

    public Order save(Order or) {
        return orderRepository.save(or);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Integer id) {
         orderRepository.deleteById(id);
    }


    public List<Product> getProductsForOrder(Integer id) {
        return null;
    }

    public void findOrderById(Integer id) {
        orderRepository.findById(id);
    }

}

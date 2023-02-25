package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByDeletedIsFalse();

    List<Product> findByNameContainingIgnoreCase(String searchText);

    Optional<Product> findById(Long id);
}

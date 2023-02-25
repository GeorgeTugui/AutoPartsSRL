package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Favorites;

public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {
}

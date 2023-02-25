package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Detailing;
import java.util.List;

public interface DetailingRepository extends JpaRepository<Detailing, Integer> {
    List<Detailing> findByDeletedIsFalse();

//    List<Detailing> findByNameContainingIgnoreCase(String searchText);
}

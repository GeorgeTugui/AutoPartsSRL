package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Exterior;
import java.util.List;

public interface ExteriorRepository extends JpaRepository<Exterior, Integer> {
    List<Exterior> findByDeletedIsFalse();

}

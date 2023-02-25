package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Interior;
import java.util.List;

public interface InteriorRepository extends JpaRepository<Interior, Integer> {
    List<Interior> findByDeletedIsFalse();

}

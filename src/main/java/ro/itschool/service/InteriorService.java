package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Interior;
import ro.itschool.repository.InteriorRepository;
import java.util.List;

@Service
public class InteriorService {
    private final InteriorRepository interiorRepository;

    public InteriorService(InteriorRepository interiorRepository) {
        this.interiorRepository = interiorRepository;
    }

    public List<Interior> getAllInteriors() {
        return interiorRepository.findAll();
    }

}

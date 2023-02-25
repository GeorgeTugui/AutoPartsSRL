package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Exterior;
import ro.itschool.repository.ExteriorRepository;
import java.util.List;

@Service
public class ExteriorService {
    private final ExteriorRepository exteriorRepository;

    public ExteriorService(ExteriorRepository exteriorRepository) {
        this.exteriorRepository = exteriorRepository;
    }

    public List<Exterior> getAllExteriors() {
        return exteriorRepository.findAll();
    }

}


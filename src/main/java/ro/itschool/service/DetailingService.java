package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Detailing;
import ro.itschool.repository.DetailingRepository;
import java.util.List;

@Service
public class DetailingService {
    private final DetailingRepository detailingRepository;

    public DetailingService(DetailingRepository detailingRepository) {
        this.detailingRepository = detailingRepository;
    }

    public List<Detailing> getAllDetailing() {
        return detailingRepository.findAll();
    }

}

package project.seatsence.src.utilization.service.walkin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.utilization.dao.walkin.WalkInRepository;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkInService {
    private final WalkInRepository walkInRepository;

    public WalkIn save(WalkIn walkIn) {
        return walkInRepository.save(walkIn);
    }
}

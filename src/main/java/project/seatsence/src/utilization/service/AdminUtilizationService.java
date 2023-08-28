package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.utilization.domain.Utilization;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUtilizationService {

    private final UtilizationService utilizationService;

    public void forceCheckOut(Long utilizationId) {
        Utilization utilization = utilizationService.findByIdAndState(utilizationId);
        utilization.forceCheckOut();
    }
}

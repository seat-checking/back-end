package project.seatsence.src.utilization.service.walkin;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.global.code.ResponseCode.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.utilization.dao.walkin.WalkInRepository;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkInService {
    private final WalkInRepository walkInRepository;

    public WalkIn save(WalkIn walkIn) {
        return walkInRepository.save(walkIn);
    }

    public WalkIn findByIdAndState(Long id) {
        return walkInRepository.findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(WALK_IN_NOT_FOUND));
    }
}

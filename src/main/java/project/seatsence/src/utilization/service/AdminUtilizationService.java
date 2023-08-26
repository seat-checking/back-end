package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.utilization.dao.UtilizationRepository;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUtilizationService {

    private final UtilizationRepository utilizationRepository;

    public Utilization findByIdAndState(Long id){
        return utilizationRepository.findByIdAndState(id,ACTIVE)
                .orElseThrow(() -> new BaseException(UTILIZATION_NOT_FOUND));
    }

    public void forcedCheckOut(Long utilizationId){
        Utilization utilization=findByIdAndState(utilizationId);
        utilization.setUtilizationStatus(UtilizationStatus.CHECK_OUT);
    }
}
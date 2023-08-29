package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.UTILIZATION_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.utilization.dao.UtilizationRepository;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;

@Service
@Transactional
@RequiredArgsConstructor
public class UtilizationService {
    private UtilizationRepository utilizationRepository;

    public Utilization findByIdAndState(Long id) {
        return utilizationRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(UTILIZATION_NOT_FOUND));
    }

    public List<Utilization> findByStoreSpaceAndUtilizationStatusOrUtilizationStatusAndState(
            StoreSpace storeSpace,
            UtilizationStatus utilizationStatus1,
            UtilizationStatus utilizationStatus2) {
        return utilizationRepository
                .findByStoreSpaceAndUtilizationStatusOrUtilizationStatusAndState(
                        storeSpace, utilizationStatus1, utilizationStatus2, ACTIVE);
    }

    public Boolean isReservation(Utilization utilization) {
        Boolean isReservation = false;
        if (utilization.getReservation() != null) {
            isReservation = true;
        }
        return isReservation;
    }

    public Boolean isWalkIn(Utilization utilization) {
        Boolean isWalkIn = false;
        if (utilization.getWalkIn() != null) {
            isWalkIn = true;
        }
        return isWalkIn;
    }
}

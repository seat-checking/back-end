package project.seatsence.src.utilization.service.walkin;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.store.domain.ReservationUnit.CHAIR;
import static project.seatsence.src.store.domain.ReservationUnit.SPACE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.utilization.dao.walkin.WalkInRepository;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkInService {
    private final WalkInRepository walkInRepository;

    public WalkIn save(WalkIn walkIn) {
        return walkInRepository.save(walkIn);
    }

    public WalkIn findByIdAndState(Long id) {
        return walkInRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(WALK_IN_NOT_FOUND));
    }

    public ReservationUnit getUtilizationUnitOfWalkIn(WalkIn walkIn) {
        ReservationUnit utilizationUnit = null;
        if (walkIn.getUsedStoreSpace() != null) {
            utilizationUnit = SPACE;
        } else if (walkIn.getUsedStoreChair() != null) {
            utilizationUnit = CHAIR;
        }
        return utilizationUnit;
    }

    public WalkIn findByUsedStoreSpaceAndEndScheduleIsAfterAndState(StoreSpace storeSpace, LocalDateTime startDateTimeToSee) {
        return walkInRepository.findByUsedStoreSpaceAndEndScheduleIsAfterAndState(storeSpace, startDateTimeToSee, ACTIVE);
    }

    public WalkIn findByUsedStoreChairAndEndScheduleIsAfterAndState(StoreChair storeChair, LocalDateTime startDateTimeToSee) {
        return walkInRepository.findByUsedStoreChairAndEndScheduleIsAfterAndState(storeChair, startDateTimeToSee, ACTIVE);
    }
}

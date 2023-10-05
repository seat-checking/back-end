package project.seatsence.src.utilization.service.walkin;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.store.domain.ReservationUnit.CHAIR;
import static project.seatsence.src.store.domain.ReservationUnit.SPACE;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.utilization.dao.walkin.WalkInRepository;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.response.AllUtilizationsForSeatAndDateResponse;
import project.seatsence.src.utilization.service.UtilizationService;

@Service
@Transactional
@RequiredArgsConstructor
public class WalkInService {
    private final WalkInRepository walkInRepository;
    private final StoreSpaceService storeSpaceService;
    private final UtilizationService utilizationService;
    private final StoreChairService storeChairService;

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

    public WalkIn findByUsedStoreSpaceAndEndScheduleIsAfterAndState(
            StoreSpace storeSpace, LocalDateTime startDateTimeToSee) {
        return walkInRepository.findByUsedStoreSpaceAndEndScheduleIsAfterAndState(
                storeSpace, startDateTimeToSee, ACTIVE);
    }

    public WalkIn findByUsedStoreChairAndEndScheduleIsAfterAndState(
            StoreChair storeChair, LocalDateTime startDateTimeToSee) {
        return walkInRepository.findByUsedStoreChairAndEndScheduleIsAfterAndState(
                storeChair, startDateTimeToSee, ACTIVE);
    }

    public AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate
            getAllWalkInsForSpaceAndDate(Long spaceId, LocalDateTime standardTime) {
        StoreSpace storeSpace = storeSpaceService.findByIdAndState(spaceId);

        WalkIn walkInFoundBySpace = // Todo : List로 바꾸기
                findByUsedStoreSpaceAndEndScheduleIsAfterAndState(storeSpace, standardTime);

        if (walkInFoundBySpace != null) {
            Utilization utilizationFoundBySpaceWalkIn =
                    utilizationService.findByWalkInIdAndState(walkInFoundBySpace.getId());
            if (utilizationFoundBySpaceWalkIn.getUtilizationStatus()
                    == UtilizationStatus.CHECK_IN) {
                return mappingFromWalkInToUtilizationForSeatAndDate(walkInFoundBySpace);
            }
        }

        List<StoreChair> storeChairList = storeChairService.findAllByStoreSpaceAndState(storeSpace);

        for (StoreChair storeChair : storeChairList) {
            WalkIn walkInFoundByChair =
                    findByUsedStoreChairAndEndScheduleIsAfterAndState(storeChair, standardTime);

            if (walkInFoundByChair != null) {
                Utilization utilizationFoundByChairWalkIn =
                        utilizationService.findByWalkInIdAndState(walkInFoundByChair.getId());
                if (utilizationFoundByChairWalkIn.getUtilizationStatus()
                        == UtilizationStatus.CHECK_IN) {
                    return mappingFromWalkInToUtilizationForSeatAndDate(walkInFoundByChair);
                }
            }
        }
        return null; // Todo : null 대체할 방법?
    }

    public AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate
            mappingFromWalkInToUtilizationForSeatAndDate(WalkIn walkIn) {
        return AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate.from(walkIn);
    }
}

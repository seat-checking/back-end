package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.UTILIZATION_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.dto.response.LoadSeatsCurrentlyInUseResponse;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.utilization.dao.UtilizationRepository;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.service.reservation.ReservationService;
import project.seatsence.src.utilization.service.walkin.WalkInService;

@Service
@Transactional
@RequiredArgsConstructor
public class UtilizationService {
    private UtilizationRepository utilizationRepository;
    private final ReservationService reservationService;
    private final WalkInService walkInService;
    private final StoreSpaceService storeSpaceService;

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

    public List<LoadSeatsCurrentlyInUseResponse.chairCurrentlyInUse> loadSeatCurrentlyInUse(Long spaceId) {
        List<Utilization> utilizations = new ArrayList<>();

        StoreSpace storeSpaceFound = storeSpaceService.findByIdAndState(spaceId);

        List<Utilization> allUtilizations =
                findByStoreSpaceAndUtilizationStatusOrUtilizationStatusAndState(
                        storeSpaceFound, UtilizationStatus.HOLDING, UtilizationStatus.CHECK_IN);

        for (Utilization utilization : allUtilizations) {
            Reservation reservation = null;
            WalkIn walkIn = null;
            ReservationUnit utilizationUnit = null;

            if (isReservation(utilization)) {
                reservation =
                        reservationService.findByIdAndState(utilization.getReservation().getId());
                utilizationUnit = utilization.getUtilizationUnit();

                switch (utilizationUnit) {
                    case SPACE:
                        break;

                    case CHAIR:
//                        allChairsCurrentlyInUse.add(reservation.getReservedStoreChair().getId());
                        break;

                }

            } else {
                walkIn = walkInService.findByIdAndState(utilization.getWalkIn().getId());
            }
        }

        List<LoadSeatsCurrentlyInUseResponse.chairCurrentlyInUse> allChairsCurrentlyInUse

        return allChairsCurrentlyInUse;
    }
}

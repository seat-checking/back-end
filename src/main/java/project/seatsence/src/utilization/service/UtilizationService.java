package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.UTILIZATION_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;
import static project.seatsence.src.store.domain.ReservationUnit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;

import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.utilization.dao.UtilizationRepository;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.response.LoadSeatsCurrentlyInUseResponse;
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

    public List<Utilization> findByStoreSpaceAndUtilizationUnitAndUtilizationStatusOrUtilizationStatusAndState(
            StoreSpace storeSpace,
            ReservationUnit utilizationUnit,
            UtilizationStatus utilizationStatus1,
            UtilizationStatus utilizationStatus2) {
        return utilizationRepository
                .findByStoreSpaceAndUtilizationUnitAndUtilizationStatusOrUtilizationStatusAndState(
                        storeSpace, utilizationUnit, utilizationStatus1, utilizationStatus2, ACTIVE);
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

    public List<LoadSeatsCurrentlyInUseResponse.ChairCurrentlyInUse> loadSeatCurrentlyInUse(Long spaceId) {
        List<LoadSeatsCurrentlyInUseResponse.ChairCurrentlyInUse> mappedUtilizations = new ArrayList<>();
        StoreSpace storeSpaceFound = storeSpaceService.findByIdAndState(spaceId);

        List<Utilization> allUtilizationsByChair =
                findByStoreSpaceAndUtilizationUnitAndUtilizationStatusOrUtilizationStatusAndState(
                        storeSpaceFound, CHAIR, UtilizationStatus.HOLDING, UtilizationStatus.CHECK_IN);


        mappedUtilizations =
                allUtilizationsByChair.stream()
                        .map(
                                utilization ->
                                        LoadSeatsCurrentlyInUseResponse.ChairCurrentlyInUse.from(utilization))
                        .collect(Collectors.toList());

        return mappedUtilizations;
    }

    /**
     * 현재 Space단위로 이용되고있는 좌석
     * @return List<Utilization>
     */
    public List<Utilization> loadSeatCurrentlyInUseAsSpaceUnit(Long spaceId) {
        StoreSpace storeSpaceFound = storeSpaceService.findByIdAndState(spaceId);

        return findByStoreSpaceAndUtilizationUnitAndUtilizationStatusOrUtilizationStatusAndState(
                        storeSpaceFound, SPACE, UtilizationStatus.HOLDING, UtilizationStatus.CHECK_IN);
    }
}

package project.seatsence.src.utilization.service.reservation;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.src.utilization.domain.reservation.ReservationStatus.PENDING;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.utilization.dao.reservation.ReservationRepository;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.request.AllReservationsForChairAndDateRequest;
import project.seatsence.src.utilization.dto.reservation.response.AllReservationsForChairAndDateResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreChairService storeChairService;

    public Reservation findById(Long id) {
        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));
    }

    public Boolean isPossibleTimeToManageReservationStatus(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(reservation.getReservationEndDateAndTime());
    }

    public Boolean isReservationStatusPending(Reservation reservation) {
        return reservation.getReservationStatus().equals(PENDING);
    }

    public void checkValidationToModifyReservationStatus(Reservation reservation) {
        if (!isReservationStatusPending(reservation)) {
            throw new BaseException(INVALID_RESERVATION_STATUS);
        }

        if (!isPossibleTimeToManageReservationStatus(reservation)) {
            throw new BaseException(INVALID_TIME_TO_MODIFY_RESERVATION_STATUS);
        }
    }

    public AllReservationsForChairAndDateResponse getAllReservationsForChairAndDate(
            AllReservationsForChairAndDateRequest allReservationsForChairAndDateRequest) {
        StoreChair storeChair = storeChairService.findByIdAndState(allReservationsForChairAndDateRequest.getReservationChairId());

    }
}

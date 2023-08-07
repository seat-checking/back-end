package project.seatsence.src.reservation.service;

import static project.seatsence.global.code.ResponseCode.RESERVATION_NOT_FOUND;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.reservation.dao.ReservationRepository;
import project.seatsence.src.reservation.domain.Reservation;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation findById(Long id) {
        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));
    }

    public Boolean isPossibleTimeToManageReservationStatus(Reservation reservation) {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(reservation.getReservationEndDateAndTime());
    }
}

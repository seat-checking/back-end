package project.seatsence.src.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.reservation.dao.ReservationRepository;
import project.seatsence.src.reservation.domain.Reservation;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatReservationService {
    private final ReservationRepository reservationRepository;

    public void seatReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }
}

package project.seatsence.src.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.reservation.dao.ReservationRepository;
import project.seatsence.src.reservation.dto.request.SeatReservationRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatReservationService {
    private final ReservationRepository reservationRepository;

    public void seatReservation(SeatReservationRequest seatReservationRequest) {}
}

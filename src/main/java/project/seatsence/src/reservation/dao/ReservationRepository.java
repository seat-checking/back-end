package project.seatsence.src.reservation.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.domain.ReservationStatus;
import project.seatsence.src.user.domain.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation save(Reservation reservation);

    List<Reservation> findAllByUserAndReservationStatusAndState(
            User user, ReservationStatus reservationStatus, State state);
}

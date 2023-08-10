package project.seatsence.src.utilization.dao.reservation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation save(Reservation reservation);

    Slice<Reservation>
            findAllByUserAndReservationStatusAndStateOrderByReservationStartDateAndTimeDesc(
                    User user, ReservationStatus reservationStatus, State state, Pageable pageable);

    Reservation findByReservedStoreChairAndUserAndState(StoreChair storeChair, User user, State state);

    Optional<Reservation> findByIdAndState(Long id, State state);

    List<Reservation> findAllByStoreIdAndState(Long storeId, State state);

    List<Reservation> findAllByStoreIdAndReservationStatus(
            Long storeId, ReservationStatus reservationStatus);

    List<Reservation> findAllByStoreIdAndReservationStatusNot(
            Long storeId, ReservationStatus reservationStatus);
    // TODO 이거 다 Store로 바꿔야함
}

package project.seatsence.src.utilization.service.reservation;

import static project.seatsence.global.code.ResponseCode.RESERVATION_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.utilization.domain.reservation.ReservationStatus.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.utilization.dao.reservation.ReservationRepository;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public void reservationApprove(Long reservationId) {
        Reservation reservation = reservationService.findByIdAndState(reservationId);
        reservation.setReservationStatus(APPROVED);
    }

    public void reservationReject(Long reservationId) {
        Reservation reservation = reservationService.findByIdAndState(reservationId);
        reservation.setReservationStatus(REJECTED);
    }

    public List<Reservation> getAllReservationAndState(Long storeId) {
        List<Reservation> reservationList = findAllByStoreIdAndState(storeId);
        if (reservationList == null || reservationList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationList;
    }

    public List<Reservation> findAllByStoreIdAndState(Long storeId) {
        return reservationRepository.findAllByStoreIdAndState(storeId, ACTIVE);
    }

    public List<Reservation> getPendingReservation(Long storeId) {
        List<Reservation> reservationPendingList =
                findAllByStoreIdAndReservationStatusAndState(storeId, PENDING);
        if (reservationPendingList == null || reservationPendingList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationPendingList;
    }

    public List<Reservation> findAllByStoreIdAndReservationStatusAndState(
            Long storeId, ReservationStatus reservationStatus) {
        return reservationRepository.findAllByStoreIdAndReservationStatusAndState(
                storeId, reservationStatus, ACTIVE);
    }

    public List<Reservation> getProcessedReservation(Long storeId) {
        List<Reservation> reservationProcessedList =
                findAllByStoreIdAndReservationStatusNotAndState(storeId, PENDING);

        if (reservationProcessedList == null || reservationProcessedList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationProcessedList;
    }

    public List<Reservation> findAllByStoreIdAndReservationStatusNotAndState(
            Long storeId, ReservationStatus reservationStatus) {
        return reservationRepository.findAllByStoreIdAndReservationStatusNotAndState(
                storeId, reservationStatus,ACTIVE);
    }
}

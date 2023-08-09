package project.seatsence.src.utilization.service.reservation;

import static project.seatsence.global.code.ResponseCode.RESERVATION_NOT_FOUND;

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
        Reservation reservation = reservationService.findById(reservationId);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
    }

    public void reservationReject(Long reservationId) {
        Reservation reservation = reservationService.findById(reservationId);
        reservation.setReservationStatus(ReservationStatus.REJECTED);
    }

    public List<Reservation> getAllReservation(Long storeId) {
        List<Reservation> reservationList = reservationRepository.findAllByStoreId(storeId);
        if (reservationList == null || reservationList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationList;
    }

    public List<Reservation> getPendingReservation(Long storeId) {
        List<Reservation> reservationPendingList =
                reservationRepository.findAllByStoreIdAndReservationStatus(
                        storeId, ReservationStatus.PENDING);
        if (reservationPendingList == null || reservationPendingList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationPendingList;
    }

    public List<Reservation> getProcessedReservation(Long storeId) {
        List<Reservation> reservationProcessedList =
                reservationRepository.findAllByStoreIdAndReservationStatusNot(
                        storeId, ReservationStatus.PENDING);

        if (reservationProcessedList == null || reservationProcessedList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationProcessedList;
    }
}

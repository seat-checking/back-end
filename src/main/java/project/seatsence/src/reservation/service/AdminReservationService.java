package project.seatsence.src.reservation.service;

import static project.seatsence.global.code.ResponseCode.RESERVATION_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.STORE_MEMBER_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.reservation.dao.ReservationRepository;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.domain.ReservationStatus;
import project.seatsence.src.reservation.dto.response.ReservationListResponse;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.store.dto.StoreMemberMapper;
import project.seatsence.src.store.dto.response.StoreMemberListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation findById(Long id) {
        return reservationRepository
                .findById(id)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));
    }

    public void reservationApprove(Long reservationId) {
        Reservation reservation = findById(reservationId);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
    }

    public void reservationReject(Long reservationId) {
        Reservation reservation = findById(reservationId);
        reservation.setReservationStatus(ReservationStatus.REJECTED);
    }

    public List<Reservation> getAllReservation(Long storeId){
        List<Reservation> reservationList =
                reservationRepository.findAllByStoreId(storeId);
        if (reservationList == null || reservationList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationList;
    }
    public List<Reservation> getPendingReservation(Long storeId){
        List<Reservation> reservationPendingList =
                reservationRepository.findAllByStoreIdAndReservationStatus(storeId,ReservationStatus.PENDING);
        if (reservationPendingList == null || reservationPendingList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationPendingList;
    }
    public List<Reservation> getProcessedReservation(Long storeId){
        List<Reservation> reservationProcessedList =
                reservationRepository.findAllByStoreIdAndReservationStatusNot(storeId,ReservationStatus.PENDING);

        if (reservationProcessedList == null || reservationProcessedList.isEmpty())
            throw new BaseException(RESERVATION_NOT_FOUND);
        return reservationProcessedList;
    }
}

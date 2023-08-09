package project.seatsence.src.utilization.dto;

import project.seatsence.src.utilization.domain.Reservation;
import project.seatsence.src.utilization.dto.response.ReservationListResponse;

public class ReservationMapper {
    public static ReservationListResponse.ReservationResponse toReservationResponse(
            Reservation reservation) {
        return ReservationListResponse.ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .reservationStatus(reservation.getReservationStatus())
                .storeSpaceId(
                        reservation.getStoreSpace() != null
                                ? reservation.getStoreSpace().getId()
                                : null)
                .storeChairId(
                        reservation.getStoreChair() != null
                                ? reservation.getStoreChair().getId()
                                : null)
                .reservationStartDateAndTime(reservation.getReservationStartDateAndTime())
                .reservationEndDateAndTime(reservation.getReservationEndDateAndTime())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}

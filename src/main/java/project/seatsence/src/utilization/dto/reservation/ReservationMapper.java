package project.seatsence.src.utilization.dto.reservation;

import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.response.ReservationListResponse;

public class ReservationMapper {
    public static ReservationListResponse.ReservationResponse toReservationResponse(
            Reservation reservation) {
        return ReservationListResponse.ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .reservationStatus(reservation.getReservationStatus())
                .storeSpaceId(
                        reservation.getReservedStoreSpace() != null
                                ? reservation.getReservedStoreSpace().getId()
                                : null)
                .storeChairId(
                        reservation.getReservedStoreChair() != null
                                ? reservation.getReservedStoreChair().getId()
                                : null)
                .reservationStartDateAndTime(reservation.getStartSchedule())
                .reservationEndDateAndTime(reservation.getEndSchedule())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}

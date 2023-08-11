package project.seatsence.src.utilization.dto.reservation;

import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.response.AdminReservationListResponse;

public class ReservationMapper {
    public static AdminReservationListResponse.ReservationResponse toReservationResponse(
            Reservation reservation) {
        return AdminReservationListResponse.ReservationResponse.builder()
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
                .startSchedule(reservation.getStartSchedule())
                .endSchedule(reservation.getEndSchedule())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}

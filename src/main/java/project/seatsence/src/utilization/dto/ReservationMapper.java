package project.seatsence.src.utilization.dto;

import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.response.reservation.AdminReservationListResponse;

public class ReservationMapper {
    public static AdminReservationListResponse.ReservationResponse toReservationResponse(
            Reservation reservation) {
        String reservationUnitReservedByUser = null;
        String reservedPlace = null;
        String storeSpaceName = null;

        if (reservation.getReservedStoreSpace() != null) {
            reservationUnitReservedByUser = "스페이스";
            reservedPlace = reservation.getReservedStoreSpace().getName();
            storeSpaceName = reservation.getReservedStoreSpace().getName();
        } else {
            reservationUnitReservedByUser = "좌석";
            reservedPlace = String.valueOf(reservation.getReservedStoreChair().getManageId());
            storeSpaceName = reservation.getReservedStoreChair().getStoreSpace().getName();
        }

        return AdminReservationListResponse.ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .reservationStatus(reservation.getReservationStatus())
                .storeSpaceName(storeSpaceName)
                .reservationUnitReservedByUser(reservationUnitReservedByUser)
                .reservedPlace(reservedPlace)
                .startSchedule(reservation.getStartSchedule())
                .endSchedule(reservation.getEndSchedule())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}

package project.seatsence.src.utilization.dto.reservation;

import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.dto.reservation.response.AdminReservationListResponse;

public class ReservationMapper {
    public static AdminReservationListResponse.ReservationResponse toReservationResponse(
            Reservation reservation) {
        String reservationUnitReservedByUser=null;
        String reservedPlace = null;
        System.out.println("reservation.getId() = " + reservation.getId());
        if(reservation.getReservedStoreSpace()!=null){
            reservationUnitReservedByUser="스페이스";
            reservedPlace=reservation.getReservedStoreSpace().getName();
            System.out.println(" 스페이스 ");
        }
        else{
            reservationUnitReservedByUser="좌석";
            System.out.println(" 좌석 ");
            reservedPlace=String.valueOf(reservation.getReservedStoreChair().getManageId());
        }

        return AdminReservationListResponse.ReservationResponse.builder()
                .id(reservation.getId())
                .name(reservation.getUser().getName())
                .reservationStatus(reservation.getReservationStatus())
                .storeName(reservation.getStore().getStoreName())
                .reservationUnitReservedByUser(reservationUnitReservedByUser)
                .reservedPlace(reservedPlace)
                .startSchedule(reservation.getStartSchedule())
                .endSchedule(reservation.getEndSchedule())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}

package project.seatsence.src.reservation.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.store.domain.ReservationUnit;

@Getter
public class UserReservationListResponse {
    private String storeName;
    private String reservationUnit;
    private String storeSpaceName;
    private String storeChairManageId;
    private LocalDateTime reservationStartDateAndTime;
    private LocalDateTime reservationEndDateAndTime;

    public UserReservationListResponse(Reservation reservation) {
        this.storeName = reservation.getStore().getName();
        this.storeSpaceName = reservation.getStoreSpace().getName();
        this.storeChairManageId = reservation.getStoreChair().getManageId();
        this.reservationStartDateAndTime = reservation.getReservationStartDateAndTime();
        this.reservationEndDateAndTime = reservation.getReservationEndDateAndTime();
    }

    public void setReservationUnit(ReservationUnit reservationUnit) {
        this.reservationUnit = reservationUnit.getValue();
    }
}

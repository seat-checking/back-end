package project.seatsence.src.reservation.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import project.seatsence.src.reservation.domain.Reservation;

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
        this.reservationStartDateAndTime = reservation.getReservationStartDateAndTime();
        this.reservationEndDateAndTime = reservation.getReservationEndDateAndTime();
    }

    public void setReservationUnit(String reservationUnit) {
        this.reservationUnit = reservationUnit;
    }

    public void setStoreSpaceName(String storeSpaceName) {
        this.storeSpaceName = storeSpaceName;
    }

    public void setStoreChairManageId(String storeChairManageId) {
        this.storeChairManageId = storeChairManageId;
    }
}

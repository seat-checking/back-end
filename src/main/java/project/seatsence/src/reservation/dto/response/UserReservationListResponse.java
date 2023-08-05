package project.seatsence.src.reservation.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.reservation.domain.Reservation;

@Getter
@Builder
public class UserReservationListResponse {
    private String storeName;
    private String reservationUnit;
    private String storeSpaceName;
    private String storeChairManageId;
    private LocalDateTime reservationStartDateAndTime;
    private LocalDateTime reservationEndDateAndTime;

    public static UserReservationListResponse from(Reservation reservation) {
        String reservationUnit = null;
        String storeSpaceName = null;
        String storeChairManageId = null;

        if (reservation.getStoreChair() == null) {
            reservationUnit = "스페이스";
            storeSpaceName = reservation.getStoreSpace().getName();
        } else if (reservation.getStoreSpace() == null) {
            reservationUnit = "좌석";
            storeChairManageId = reservation.getStoreChair().getManageId();
        }

        return UserReservationListResponse.builder()
                .storeName(reservation.getStore().getName())
                .reservationUnit(reservationUnit)
                .storeSpaceName(storeSpaceName)
                .storeChairManageId(storeChairManageId)
                .reservationStartDateAndTime(reservation.getReservationStartDateAndTime())
                .reservationEndDateAndTime(reservation.getReservationEndDateAndTime())
                .build();
    }
}

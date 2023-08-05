package project.seatsence.src.reservation.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReservationListResponse {
    private String storeName;
    private String storeSpaceName;
    private String storeChairManageId;
    private LocalDateTime reservationStartDateAndTime;
    private LocalDateTime reservationEndDateAndTime;
}

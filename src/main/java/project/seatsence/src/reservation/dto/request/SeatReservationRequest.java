package project.seatsence.src.reservation.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SeatReservationRequest {
    @NotNull private Long storeChairId;
    @NotNull private Long userId;

    @NotNull private String reservationStartDateAndTime;
    @NotNull private String reservationEndDateAndTime;
}

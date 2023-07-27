package project.seatsence.src.reservation.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SeatReservationRequest {
    @NotNull private Long storeChairId;
    @NotNull private Long userId;

    @NotNull private LocalDateTime reservationStartDateAndTime;
    @NotNull private LocalDateTime reservationEndDateAndTime;
}

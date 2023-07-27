package project.seatsence.src.reservation.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SeatReservationRequest {
    private Long storeChairId;
    private Long userId;

    @NotNull private LocalDateTime reservationStartDateAndTime;
    private LocalDateTime reservationEndDateAndTime;
}

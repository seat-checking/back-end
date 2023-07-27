package project.seatsence.src.reservation.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class SeatReservationRequest {
    private Long storeChairId;
    private Long userId;

    @NotNull
    private LocalDateTime reservationStartDateAndTime;
    private LocalDateTime reservationEndDateAndTime;
}

package project.seatsence.src.reservation.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Getter
public class SeatReservationRequest {
    @NotNull private Long storeChairId;
    @NotNull private Long userId;

    @NotNull private LocalDateTime reservationStartDateAndTime;
    @NotNull private LocalDateTime reservationEndDateAndTime;
}

package project.seatsence.src.reservation.dto.request;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpaceReservationRequest {
    @NotNull private Long storeSpaceId;
    @NotNull private Long userId;
    @NotNull private LocalDateTime reservationStartDateAndTime;
    @NotNull private LocalDateTime reservationEndDateAndTime;
}

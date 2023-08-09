package project.seatsence.src.utilization.dto.reservation.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AllReservationsForChairAndDateRequest {
    private LocalDate reservationDate;
    private Long reservationChairId;
}

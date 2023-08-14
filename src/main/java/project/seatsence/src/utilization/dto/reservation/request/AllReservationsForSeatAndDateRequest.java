package project.seatsence.src.utilization.dto.reservation.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AllReservationsForSeatAndDateRequest {
    private LocalDateTime reservationDateAndTime;
    private Long seatIdToReservation; // 관리 id 아니고, 식별자
}

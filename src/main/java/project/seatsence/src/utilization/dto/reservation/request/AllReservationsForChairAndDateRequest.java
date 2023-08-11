package project.seatsence.src.utilization.dto.reservation.request;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AllReservationsForChairAndDateRequest {
    private LocalDateTime reservationDateAndTime;
    private Long reservationChairId; // 관리 id 아니고, StoreChair의 식별자
}

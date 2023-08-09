package project.seatsence.src.utilization.dto.reservation.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllReservationsForChairAndDateResponse {
    private List<ReservationForChairAndDate> allReservationsForChairAndDate;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReservationForChairAndDate {
        private LocalDateTime reservationStartDateAndTime;
        private LocalDateTime reservationEndDateAndTime;
    }
}

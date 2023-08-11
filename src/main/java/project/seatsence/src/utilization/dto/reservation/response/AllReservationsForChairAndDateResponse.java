package project.seatsence.src.utilization.dto.reservation.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.reservation.Reservation;

@Getter
@AllArgsConstructor
public class AllReservationsForChairAndDateResponse {
    private List<ReservationForChairAndDate> allReservationsForChairAndDate;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReservationForChairAndDate {
        private LocalDateTime startSchedule;
        private LocalDateTime endSchedule;

        public static ReservationForChairAndDate from(Reservation reservation) {
            return ReservationForChairAndDate.builder()
                    .startSchedule(reservation.getStartSchedule())
                    .endSchedule(reservation.getEndSchedule())
                    .build();
        }
    }
}

package project.seatsence.src.utilization.dto.response.reservation;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.reservation.Reservation;

@Getter
@AllArgsConstructor
public class AllReservationsForSeatAndDateResponse {
    private List<ReservationForSeatAndDate> allReservationsForSeatAndDate;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReservationForSeatAndDate {
        private LocalDateTime startSchedule;
        private LocalDateTime endSchedule;

        public static ReservationForSeatAndDate from(Reservation reservation) {
            return ReservationForSeatAndDate.builder()
                    .startSchedule(reservation.getStartSchedule())
                    .endSchedule(reservation.getEndSchedule())
                    .build();
        }
    }
}

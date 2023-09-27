package project.seatsence.src.utilization.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.Utilization;

@Getter
@AllArgsConstructor
public class AllUtilizationsForSeatAndDateResponse {
    private List<UtilizationForSeatAndDate> allUtilizationsForSeatAndDate;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class UtilizationForSeatAndDate {
        private LocalDateTime startSchedule;
        private LocalDateTime endSchedule;

        public static UtilizationForSeatAndDate from(Utilization utilization) {
            return UtilizationForSeatAndDate.builder()
                    .startSchedule(utilization.getStartSchedule())
                    .endSchedule(utilization.getEndSchedule())
                    .build();
        }
    }
}

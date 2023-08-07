package project.seatsence.src.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.src.reservation.domain.ReservationStatus;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReservationListResponse {
    private List<ReservationResponse> reservationResponseList;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ReservationResponse {
        private Long id;
        private String name;
        private ReservationStatus reservationStatus;
        @Nullable
        private Long storeSpaceId;
        @Nullable
        private Long storeChairId;
        private LocalDateTime reservationStartDateAndTime;
        private LocalDateTime reservationEndDateAndTime;
        private LocalDateTime createdAt;
    }
}

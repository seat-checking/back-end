package project.seatsence.src.utilization.dto.reservation.response;

import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;

@Getter
@AllArgsConstructor
@Builder
public class ReservationListResponse { // Todo : UserReservationListRes랑 헷갈리지않게 이름 구체화 필요
    private List<ReservationResponse> reservationResponseList;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReservationResponse {
        private Long id;
        private String name;
        private ReservationStatus reservationStatus;
        @Nullable private Long storeSpaceId;
        @Nullable private Long storeChairId;
        private LocalDateTime
                reservationStartDateAndTime; // Todo : startSchecule / endSchecule 로 수정
        private LocalDateTime reservationEndDateAndTime;
        private LocalDateTime createdAt;
    }
}

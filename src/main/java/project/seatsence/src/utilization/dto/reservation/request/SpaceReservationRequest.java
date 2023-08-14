package project.seatsence.src.utilization.dto.reservation.request;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SpaceReservationRequest {
    @NotNull
    @Parameter(name = "스페이스 식별자", description = "예약하려는 가게의 스페이스 id", required = true, example = "1")
    private Long storeSpaceId;

    @NotNull
    @Parameter(
            name = "예약 시작 일정",
            description = "예약 시작 날짜와 시간",
            required = true,
            example = "2023-08-07T10:30:00.000")
    private LocalDateTime startSchedule;

    @NotNull
    @Parameter(
            name = "예약 끝 일정",
            description = "예약 끝 날짜와 시간",
            required = true,
            example = "2023-08-07T11:30:00.000")
    private LocalDateTime endSchedule;
}

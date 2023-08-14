package project.seatsence.src.utilization.dto.reservation.request;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChairReservationRequest {
    @NotNull(message = "의자 식별자가 입력되지 않았습니다.")
    @Parameter(
            name = "의자 식별자",
            description = "예약하려는 가게 의자의 id (의자 관리id 아님)",
            required = true,
            example = "1")
    private Long storeChairId;

    @NotNull(message = "예약 시작 일정이 입력되지 않았습니다.")
    @Parameter(
            name = "예약 시작 일정",
            description = "예약 시작 날짜와 시간",
            required = true,
            example = "2023-08-07T10:30:00.000")
    private LocalDateTime startSchedule;

    @NotNull(message = "예약 끝 일정이 입력되지 않았습니다.")
    @Parameter(
            name = "예약 끝 일정",
            description = "예약 끝 날짜와 시간",
            required = true,
            example = "2023-08-07T11:30:00.000")
    private LocalDateTime endSchedule;
}

package project.seatsence.src.utilization.dto.request.reservation;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AllReservationsForSeatAndDateRequest {

    @NotNull(message = "이용하고자 하는 시간과 날짜가 입력되지않았습니다.")
    @Parameter(
            name = "이용하려는 시간과 날짜",
            description = "이용(예약 or 바로 사용)하려는 시간과 날짜 / 당일이면 시간은 현재시간으로, 다른 날이면 해당 날의 00시00분00초로 요청",
            required = true,
            example = "2023-08-07T10:30:00.000")
    private LocalDateTime reservationDateAndTime;

    @NotNull(message = "이용하고자 하는 좌석(의자 또는 스페이스)의 식별자가 입력되지 않았습니다.")
    @Parameter(
            name = "이용하려는 좌석의 식별자",
            description = "이용하려는 좌석(의자 또는 스페이스)의 식별자",
            required = true,
            example = "10")
    private Long seatIdToReservation; // 관리 id 아니고, 식별자
}

package project.seatsence.src.utilization.dto.request.participation;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserParticipationRequest {

    @NotNull private Long id;

    @NotNull(message = "예약/바로 사용")
    private String utilizationUnit; // 예약 또는 바로 사용
}

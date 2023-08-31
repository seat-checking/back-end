package project.seatsence.src.utilization.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import project.seatsence.src.utilization.dto.request.CustomUtilizationContentRequest;

@Getter
public class SpaceUtilizationRequest {
    @NotNull(message = "스페이스 식별자가 입력되지 않았습니다.")
    @Parameter(name = "스페이스 식별자", description = "이용하려는 가게의 스페이스 id", required = true, example = "1")
    private Long storeSpaceId;

    @NotNull(message = "이용 시작 일정이 입력되지 않았습니다.")
    @Parameter(
            name = "이용 시작 일정",
            description = "이용 시작 날짜와 시간",
            required = true,
            example = "2023-08-07T10:30:00.000")
    private LocalDateTime startSchedule;

    @NotNull(message = "이용 끝 일정이 입력되지 않았습니다.")
    @Parameter(
            name = "이용 끝 일정",
            description = "이용 끝 날짜와 시간",
            required = true,
            example = "2023-08-07T11:30:00.000")
    private LocalDateTime endSchedule;

    private List<CustomUtilizationContentRequest> customUtilizationContents;
}

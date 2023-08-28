package project.seatsence.src.utilization.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UtilizationStatus {
    HOLDING("HOLDING", "홀딩"),
    CHECK_IN("CHECK_IN", "사용"),
    CHECK_OUT("CHECK_OUT", "퇴실"),
    FORCED_CHECK_OUT("FORCED_CHECK_OUT", "강제 퇴실"),
    NOT_CERTIFIED("NOT_CERTIFIED", "인증 미완료");

    private String value;
    @JsonValue private String kr;
}

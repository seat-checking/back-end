package project.seatsence.src.utilization.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HoldingStatus {
    BEFORE("BEFORE", "홀딩 전"),
    IN_PROCESSING("IN_PROCESSING", "홀딩 중"),
    PROCESSED("PROCESSED", "처리됨");

    private String value;

    @JsonValue private String kr;
}

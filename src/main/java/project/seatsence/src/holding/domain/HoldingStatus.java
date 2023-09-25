package project.seatsence.src.holding.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HoldingStatus {
    IN_PROCESSING("IN_PROCESSING", "홀딩 중"),
    NOT_CERTIFIED("NOT_CERTIFIED", "인증 미완료"),
    PROCESSED("PROCESSED", "처리됨");

    private String value;

    @JsonValue private String kr;
}

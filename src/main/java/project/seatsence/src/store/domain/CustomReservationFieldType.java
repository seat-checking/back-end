package project.seatsence.src.store.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomReservationFieldType {
    TEXT("TEXT", "자유 입력"),
    DROP_DOWN("DROP_DOWN", "선택지 제공");

    private final String value;

    @JsonValue private String kr;
}

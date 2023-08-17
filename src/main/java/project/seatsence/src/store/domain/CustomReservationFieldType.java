package project.seatsence.src.store.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomReservationFieldType {
    TEXT("TEXT", "자유형식"),
    DROP_DOWN("DROP_DOWN", "선택지제공");

    private final String value;

    @JsonValue private String kr;
}

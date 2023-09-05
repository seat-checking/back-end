package project.seatsence.src.store.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomUtilizationFieldType {
    TEXT("TEXT", "자유 입력"),
    MULTI_OPTION("MULTI_OPTION", "선택지 제공");

    private final String value;

    @JsonValue private String kr;
}

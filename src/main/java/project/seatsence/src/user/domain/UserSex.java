package project.seatsence.src.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSex {
    // 여성 (F)
    F("F", "여성"),

    // 남성 (M)
    M("M", "남성");

    private String value;

    @JsonValue private String kr;
}

package project.seatsence.src.user.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    // 관리자
    ADMIN("ADMIN", "관리자"),

    // 유저
    USER("USER", "유저");

    private String value;

    @JsonValue
    private String kr;
}

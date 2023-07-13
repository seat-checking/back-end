package project.seatsence.src.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN");
    private final String value;
}

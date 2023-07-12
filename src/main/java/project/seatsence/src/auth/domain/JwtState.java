package project.seatsence.src.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum JwtState {
    DENIED("DENIED"),
    ACCESS("ACCESS"),
    EXPIRED("EXPIRED");
    private final String value;
}

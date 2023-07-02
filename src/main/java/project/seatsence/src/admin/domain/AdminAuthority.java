package project.seatsence.src.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminAuthority {
    OWNER("OWNER"),
    MEMBER("MEMBER");

    private final String value;
}

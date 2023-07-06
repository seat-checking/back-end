package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreAuthority {
    OWNER("OWNER"),
    MEMBER("MEMBER");

    private final String value;
}

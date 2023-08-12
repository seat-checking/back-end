package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomReservationType {
    TEXT("TEXT"),
    DROP_DOWN("DROP_DOWN");

    private final String value;
}
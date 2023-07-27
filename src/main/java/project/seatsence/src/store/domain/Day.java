package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Day {
    SUN("SUN", 1),
    MON("MON", 2),
    TUE("TUE", 3),
    WED("WED", 4),
    THU("THU", 5),
    FRI("FRI", 6),
    SAT("SAT", 7);

    private final String value;
    private final int dayOfWeek;
}

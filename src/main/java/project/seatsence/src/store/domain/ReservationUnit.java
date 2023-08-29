package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationUnit {
    SPACE("스페이스"),
    SEAT("좌석"),
    CHAIR("의자"),
    BOTH("스페이스/좌석");

    private final String value;
}

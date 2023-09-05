package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationUnit { // Todo : Reservation -> Utilization
    SPACE("스페이스"),
    SEAT("좌석"), // Todo : 삭제 및 이용중이던 부분 CHAIR로 변경
    CHAIR("의자"),
    BOTH("스페이스/좌석");

    private final String value;
}

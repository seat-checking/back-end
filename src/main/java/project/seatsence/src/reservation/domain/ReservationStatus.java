package project.seatsence.src.reservation.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("PENDING", "대기"),
    APPROVED("APPROVED", "승인"),
    REJECTED("REJECTED", "거절");

    private String value;

    @JsonValue private String kr;
}

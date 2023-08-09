package project.seatsence.src.utilization.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationStatus {
    PENDING("PENDING", "대기"), // 기본값
    CANCELED("CANCELED", "취소"), // 유저 직접 취소
    APPROVED("APPROVED", "승인"), // 관리자 승인
    REJECTED("REJECTED", "거절"); // 관리자 거절

    private String value;

    @JsonValue private String kr;

    private static final Map<String, ReservationStatus> BY_KR =
            Stream.of(values())
                    .collect(Collectors.toMap(ReservationStatus::getKr, Function.identity()));

    public static ReservationStatus valueOfKr(String kr) {
        return BY_KR.get(kr);
    }
}

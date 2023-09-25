package project.seatsence.src.utilization.domain.Participation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParticipationStatus {
    UPCOMING_PARTICIPATION("UPCOMING_PARTICIPATION", "참여 전"),
    PARTICIPATED("PARTICIPATED", "참여 완료");

    private String value;

    @JsonValue private String kr;
}

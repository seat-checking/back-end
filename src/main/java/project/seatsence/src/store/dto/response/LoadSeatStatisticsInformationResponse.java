package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoadSeatStatisticsInformationResponse {
    private int totalNumberOfSeats; // 스페이스 단위
    private int numberOfSeatsInUse; // 스페이스 단위
    private int averageSeatUsageTime; // 가게단위
}

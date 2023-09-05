package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoadSeatStatisticsInformationResponse {
    private int totalNumberOfSeats;
    private int numberOfSeatsInUse;
    private int averageSeatUsageTime;
}

package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoadSeatStatisticsInformationOfStoreResponse {
    private int totalNumberOfSeats;
    private int numberOfRemainingSeats;
    private int averageSeatUsageTime;
}

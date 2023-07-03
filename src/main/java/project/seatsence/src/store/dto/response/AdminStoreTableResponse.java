package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStoreTableResponse {

    private Long storeTableId;

    private int tableX;

    private int tableY;

    private List<AdminStoreChairResponse> chairList;
}

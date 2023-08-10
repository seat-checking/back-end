package project.seatsence.src.store.dto.response;

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
    private String manageId;
    private int width;
    private int height;
    private int tableX;
    private int tableY;
}

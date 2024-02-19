package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllStoreSpaceTableChairResponse {
    // store info
    private Long storeId;
    private String storeName;

    // space info
    private Long spaceId;
    private String spaceName;
    private int height;

    // table info
    private Long tableId;
    private int tableHeight;
    private int tableWidth;
    private int tableX;
    private int tableY;

    //chair info
    private Long chairId;
    private int chairX;
    private int chairY;

}

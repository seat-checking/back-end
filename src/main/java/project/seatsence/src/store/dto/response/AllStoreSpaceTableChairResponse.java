package project.seatsence.src.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AllStoreSpaceTableChairResponse {
    // store info
    private Long storeId;
    private String storeName;

    // space info
    private Long spaceId;
    private String spaceName;
    private int spaceHeight;

    // table info
    private Long tableId;
    private int tableHeight;
    private int tableWidth;
    private int tableX;
    private int tableY;

    // chair info
    private Long chairId;
    private int chairX;
    private int chairY;

    @Builder
    public AllStoreSpaceTableChairResponse(
            Long storeId,
            String storeName,
            Long spaceId,
            String spaceName,
            int spaceHeight,
            Long tableId,
            int tableHeight,
            int tableWidth,
            int tableX,
            int tableY) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.spaceHeight = spaceHeight;
        this.tableId = tableId;
        this.tableHeight = tableHeight;
        this.tableWidth = tableWidth;
        this.tableX = tableX;
        this.tableY = tableY;
    }

    @Builder
    public AllStoreSpaceTableChairResponse(
            Long storeId,
            String storeName,
            Long spaceId,
            String spaceName,
            int spaceHeight,
            Long chairId,
            int chairX,
            int chairY) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.spaceId = spaceId;
        this.spaceName = spaceName;
        this.spaceHeight = spaceHeight;
        this.chairId = chairId;
        this.chairX = chairX;
        this.chairY = chairY;
    }
}

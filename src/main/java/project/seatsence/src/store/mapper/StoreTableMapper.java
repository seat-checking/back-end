package project.seatsence.src.store.mapper;

import project.seatsence.src.store.domain.StoreTable;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceTableResponse;

public class StoreTableMapper {
    public static StoreSpaceTableResponse convertToStoreSpaceTableResponse(StoreTable storeTable) {
        return StoreSpaceTableResponse.builder()
                .id(storeTable.getId())
                .width(storeTable.getWidth())
                .height(storeTable.getHeight())
                .tableX(storeTable.getTableX())
                .tableY(storeTable.getTableY())
                .build();
    }
}

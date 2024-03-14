package project.seatsence.src.store.mapper;

import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.dto.response.admin.space.StoreSpaceChairResponse;

public class StoreChairMapper {
    public static StoreSpaceChairResponse convertToStoreSpaceChairResponse(StoreChair storeChair) {
        return StoreSpaceChairResponse.builder()
                .id(storeChair.getId())
                .manageId(storeChair.getManageId())
                .chairX(storeChair.getChairX())
                .chairY(storeChair.getChairY())
                .build();
    }
}

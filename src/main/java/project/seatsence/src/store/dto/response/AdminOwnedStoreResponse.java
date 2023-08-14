package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminOwnedStoreResponse {

    private List<StoreResponse> storeResponseList;

    @AllArgsConstructor
    @Getter
    public static class StoreResponse {
        private Long storeId;
        private String storeName;
        boolean isOpenNow;
        boolean isClosedToday;
    }
}

package project.seatsence.src.store.dto.request.admin.space;

import java.util.List;
import lombok.Getter;
import project.seatsence.src.store.dto.request.admin.basic.StoreReservationUnitRequest;

@Getter
public class StoreSpaceUpdateRequest {

    private String storeSpaceName;
    private StoreReservationUnitRequest reservationUnit;
    private int height;
    List<StoreSpaceTableRequest> tableList;
    List<StoreSpaceChairRequest> chairList;
}

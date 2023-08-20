package project.seatsence.src.store.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class AdminStoreSpaceUpdateRequest {

    private String storeSpaceName;
    private ReservationUnitRequest reservationUnit;
    private int height;
    List<AdminStoreSpaceTableRequest> tableList;
    List<AdminStoreSpaceChairRequest> chairList;
}

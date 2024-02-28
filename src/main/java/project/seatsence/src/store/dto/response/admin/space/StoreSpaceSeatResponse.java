package project.seatsence.src.store.dto.response.admin.space;

import java.io.Serializable;
import java.util.List;
import lombok.*;
import project.seatsence.src.store.dto.response.admin.basic.StoreReservationUnitResponse;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSpaceSeatResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long storeSpaceId;

    private String storeSpaceName;

    private int height;

    private StoreReservationUnitResponse reservationUnit;

    private List<StoreSpaceTableResponse> tableList;

    private List<StoreSpaceChairResponse> chairList;
}

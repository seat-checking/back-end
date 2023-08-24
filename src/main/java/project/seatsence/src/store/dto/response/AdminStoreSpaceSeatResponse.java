package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreSpaceSeatResponse {

    private Long storeSpaceId;

    private String storeSpaceName;

    private int height;

    private ReservationUnitResponse reservationUnit;

    private List<AdminStoreSpaceTableResponse> tableList;

    private List<AdminStoreSpaceChairResponse> chairList;
}

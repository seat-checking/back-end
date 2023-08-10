package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreSpaceResponse {

    private Long storeSpaceId;

    private String storeSpaceName;

    private int width;

    private int height;

    private List<AdminStoreTableResponse> tableList;

    private List<AdminStoreChairResponse> chairList;
}

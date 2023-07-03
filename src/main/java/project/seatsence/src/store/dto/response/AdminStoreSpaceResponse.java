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
    private String name;

    private int width;

    private int height;

    private int entranceX;

    private int entranceY;

    private List<AdminStoreTableResponse> tableList;
}

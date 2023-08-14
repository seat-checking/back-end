package project.seatsence.src.store.dto.response;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStoreFormResponse {

    private Long storeId;

    private List<AdminStoreSpaceSeatResponse> adminStoreSpaceSeatResponseList;
}

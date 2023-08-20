package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreSpaceChairResponse {

    private String i;
    private int manageId;
    private int x;
    private int y;
}

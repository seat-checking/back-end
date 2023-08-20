package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStoreSpaceTableResponse {

    private String i;
    private int w;
    private int h;
    private int x;
    private int y;
}

package project.seatsence.src.store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminStoreSpaceChairResponse {

    @JsonProperty("i")
    private String ibByWeb; // 프론트엔드에서 관리용으로 사용하는 id

    private int manageId;

    @JsonProperty("x")
    private int chairX;

    @JsonProperty("y")
    private int chairY;
}

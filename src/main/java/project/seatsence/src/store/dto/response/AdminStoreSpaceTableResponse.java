package project.seatsence.src.store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminStoreSpaceTableResponse {

    @JsonProperty("i")
    private String idByWeb; // 프론트엔드에서 관리용으로 사용하는 id

    @JsonProperty("w")
    private int width;

    @JsonProperty("h")
    private int height;

    @JsonProperty("x")
    private int tableX;

    @JsonProperty("y")
    private int tableY;
}

package project.seatsence.src.store.dto.request.admin.space;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class StoreSpaceChairRequest {

    @JsonProperty("i")
    @NotBlank
    private String idByWeb; // 프론트엔드에서 관리용으로 사용하는 id

    @PositiveOrZero int manageId;

    @JsonProperty("x")
    @PositiveOrZero
    private int chairX;

    @JsonProperty("y")
    @PositiveOrZero
    private int chairY;
}

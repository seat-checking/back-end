package project.seatsence.src.store.dto.response.admin.space;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSpaceChairResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("i")
    private Long id; // store chair primary key

    private int manageId;

    @JsonProperty("x")
    private int chairX;

    @JsonProperty("y")
    private int chairY;
}

package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class AdminStoreSpaceChairRequest {

    @NotBlank private String i;
    @PositiveOrZero int manageId;
    @PositiveOrZero private int x;
    @PositiveOrZero private int y;
}

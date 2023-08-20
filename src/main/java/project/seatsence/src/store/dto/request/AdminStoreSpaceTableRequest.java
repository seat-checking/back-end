package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class AdminStoreSpaceTableRequest {
    @NotBlank private String i;
    @PositiveOrZero private int x;
    @PositiveOrZero private int y;
    @PositiveOrZero private int w;
    @PositiveOrZero private int h;
}

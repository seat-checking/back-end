package project.seatsence.src.store.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class AdminStoreSpaceUpdateRequest {

    private String name;
    private String reservationUnit;
    private int height;

    List<AdminStoreSpaceUpdateRequest.Table> tableList;

    List<AdminStoreSpaceUpdateRequest.Chair> chairList;

    @Validated
    @Getter
    public static class Table {
        @NotBlank private String storeTableId;
        @PositiveOrZero private int tableX;
        @PositiveOrZero private int tableY;
        @PositiveOrZero private int tableWidth;
        @PositiveOrZero private int tableHeight;
    }

    @Validated
    @Getter
    public static class Chair {
        @NotBlank private String storeChairId;
        @PositiveOrZero int manageId;
        @PositiveOrZero private int chairX;
        @PositiveOrZero private int chairY;
    }
}

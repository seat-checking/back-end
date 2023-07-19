package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StoreMemberUpdateRequest {

    @NotNull private Long id;

    @NotNull private PermissionByMenu permissionByMenu;

    @Getter
    @Setter
    public static class PermissionByMenu {

        @NotNull private Boolean storeStatus;
        @NotNull private Boolean seatSetting;
        @NotNull private Boolean storeStatistics;
        @NotNull private Boolean storeSetting;
    }
}

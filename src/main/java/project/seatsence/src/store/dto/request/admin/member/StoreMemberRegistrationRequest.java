package project.seatsence.src.store.dto.request.admin.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.global.annotation.ValidEmail;

@Data
@Getter
@Setter
public class StoreMemberRegistrationRequest {

    @ValidEmail
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;

    @NotNull private PermissionByMenu permissionByMenu;

    @Getter
    @Setter
    public static class PermissionByMenu {

        @NotNull private Boolean storeStatus;
        @NotNull private Boolean seatSetting;
        @NotNull private Boolean storeStatistics;
        @NotNull private Boolean storeSetting;
    }

    // TODO Setter 지우기
}

package project.seatsence.src.store.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;

@Data
@Getter
public class StoreMemberRegistrationRequest {

    @ValidEmail
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;

    private Boolean storeStatus;

    private Boolean seatSetting;

    private Boolean storeStatistics;

    private Boolean storeSetting;
}

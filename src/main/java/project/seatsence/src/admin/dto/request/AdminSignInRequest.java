package project.seatsence.src.admin.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidPassword;

@Data
@Getter
public class AdminSignInRequest {

    @NotNull(message = "이메일이 입력되지 않았습니다.")
    @ValidEmail
    private String email;

    @NotNull(message = "비밀번호가 입력되지 않았습니다.")
    @ValidPassword
    private String password;
}

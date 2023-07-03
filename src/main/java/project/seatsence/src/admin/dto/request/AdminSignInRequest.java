package project.seatsence.src.admin.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AdminSignInRequest {

    @NotNull(message = "이메일이 입력되지 않았습니다.")
    private String email;

    @NotNull(message = "비밀번호가 입력되지 않았습니다.")
    private String password;
}

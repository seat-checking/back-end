package project.seatsence.src.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidPassword;

@Data
@Getter
public class UserSignInRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @ValidEmail
    @Parameter(description = "유저 이메일", required = true, example = "test@naver.com")
    private String email;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @ValidPassword
    @Parameter(description = "유저 비밀번호", required = true, example = "testPassword")
    private String password;
}

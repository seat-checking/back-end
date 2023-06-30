package project.seatsence.src.user.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;

@Data
@Getter
public class UserSignInRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @ValidEmail
    private String email;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;

    @Builder
    public UserSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

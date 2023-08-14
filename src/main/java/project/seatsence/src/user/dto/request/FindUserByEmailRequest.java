package project.seatsence.src.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;

@Data
@Getter
public class FindUserByEmailRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @ValidEmail
    @Parameter(name = "이메일", description = "유저 이메일", required = true, example = "test@naver.com")
    private String email;
}

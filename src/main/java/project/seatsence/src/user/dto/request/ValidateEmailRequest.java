package project.seatsence.src.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.annotation.ValidEmail;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateEmailRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @ValidEmail
    @Parameter(description = "사용하려는 이메일", required = true, example = "test@naver.com")
    private String email;
}

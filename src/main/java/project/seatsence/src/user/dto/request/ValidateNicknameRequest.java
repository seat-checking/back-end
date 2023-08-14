package project.seatsence.src.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.annotation.ValidNickname;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateNicknameRequest {

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    @ValidNickname
    @Parameter(
            name = "유저 닉네임",
            description = "사용하려는 닉네임",
            required = true,
            example = "testNickname")
    private String nickname;
}

package project.seatsence.src.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Getter
@Data
public class UserSignUpRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @ValidPassword
    private String password;

    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    private String nickname;

    @NotNull(message = "나이가 입력되지 않았습니다.")
    private int age;

    @NotNull(message = "성별이 체크되지 않았습니다.")
    private UserSex sex;

    @NotNull(message = "마케팅 정보 수신 동의 여부가 체크되지 않았습니다.")
    private Boolean consentToMarketing;

    @NotNull(message = "이용 약관 동의 여부가 체크되지 않았습니다.")
    private Boolean consentToTermsOfUser;
}

package project.seatsence.src.user.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidBirthDate;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidNickname;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Getter
@Data
public class UserSignUpRequest {
    @ValidEmail
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    @Parameter(description = "사용하려는 이메일", required = true, example = "test@naver.com")
    private String email;

    @ValidPassword
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Parameter(description = "사용하려는 비밀번호", required = true, example = "testPassword")
    private String password;

    @ValidNickname
    @NotBlank(message = "닉네임이 입력되지 않았습니다.")
    @Parameter(description = "사용하려는 닉네임", required = true, example = "testNickname")
    private String nickname;

    @NotBlank(message = "이름이 입력되지 않았습니다.")
    @Parameter(description = "유저의 본명", required = true, example = "김철수")
    private String name;

    @ValidBirthDate
    @NotBlank(message = "생년월일이 입력되지 않았습니다.")
    @Parameter(description = "YYYY-MM-DD 형태의 유저 생년월일", required = true, example = "2023-08-14")
    private String birthDate;

    @NotNull(message = "성별이 체크되지 않았습니다.")
    @Parameter(description = "유저 성별 / '남성' 혹은 '여성' 입력가능", required = true, example = "여성")
    private UserSex sex;

    @NotNull(message = "마케팅 정보 수신 동의 여부는 빈 값일 수 없습니다.")
    @Parameter(description = "마케팅 정보 수신 동의 여부", required = true, example = "false")
    private Boolean consentToMarketing;

    @NotNull(message = "이용 약관 동의 여부는 빈 값일 수 없습니다.")
    @Parameter(description = "이용 약관 동의 여부", required = true, example = "true")
    private Boolean consentToTermsOfUser;
}

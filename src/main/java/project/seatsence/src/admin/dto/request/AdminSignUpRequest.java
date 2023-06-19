package project.seatsence.src.admin.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidEmployerIdNumber;
import project.seatsence.global.annotation.ValidNickname;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Data
@Getter
public class AdminSignUpRequest {
    @ValidEmail(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @ValidPassword(message = "비밀번호는 8자 이상의 영문 + 숫자 + 특수기호 조합만 허용합니다.")
    private String password;

    private String passwordChecked;

    @ValidNickname(message = "닉네임 형식이 맞지 않습니다.")
    private String nickname;

    @NotNull(message = "나이가 입력되지 않았습니다.")
    private int age;

    @NotNull(message = "성별이 선택되지 않았습니다.")
    private UserSex sex;

    private Boolean consentToMarketing;

    @NotNull(message = "이용 약관 동의는 필수입니다.")
    private Boolean consentToTermsOfUser;

    @ValidEmployerIdNumber(message = "사업자등록번호는 10자리의 숫자입니다.")
    @NotBlank(message = "사업자등록번호가 입력되지 않았습니다.")
    private String employerIdNumber;

    @NotBlank(message = "오픈일자가 입력되지 않았습니다.")
    private String openDate;

    @NotBlank(message = "대표자 이름이 입력되지 않았습니다.")
    private String adminName;
}

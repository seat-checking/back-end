package project.seatsence.src.admin.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidBusinessRegistrationNumber;
import project.seatsence.global.annotation.ValidNickname;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Data
@Getter
public class AdminSignUpRequest {
    @ValidEmail
    private String email;

    @ValidPassword
    private String password;

    private String passwordChecked;

    @ValidNickname
    private String nickname;

    @NotNull(message = "나이가 입력되지 않았습니다.")
    private int age;

    @NotNull(message = "성별이 선택되지 않았습니다.")
    private UserSex sex;

    private Boolean consentToMarketing;

    @NotNull(message = "이용 약관 동의는 필수입니다.")
    private Boolean consentToTermsOfUser;

    @ValidBusinessRegistrationNumber
    @NotBlank(message = "사업자등록번호가 입력되지 않았습니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "개업일자가 입력되지 않았습니다.")
    private String openDate;

    @NotBlank(message = "대표자명이 입력되지 않았습니다.")
    private String adminName;
}

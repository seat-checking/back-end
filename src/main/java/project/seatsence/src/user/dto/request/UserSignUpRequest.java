package project.seatsence.src.user.dto.request;

import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidEmail;
import project.seatsence.global.annotation.ValidNickname;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Getter
@Data
public class UserSignUpRequest {
    @ValidEmail private String email;
    @ValidPassword private String password;
    @ValidNickname private String nickname;
    private int age;
    private UserSex sex;
    private Boolean consentToMarketing;
    private Boolean consentToTermsOfUser;
}

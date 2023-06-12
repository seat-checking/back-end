package project.seatsence.src.user.dto.request;

import lombok.Data;
import lombok.Getter;
import project.seatsence.global.annotation.ValidPassword;
import project.seatsence.src.user.domain.UserSex;

@Getter
@Data
public class UserSignUpRequest {
    private String email;
    @ValidPassword private String password;
    private String nickname;
    private int age;
    private UserSex sex;
    private Boolean consentToMarketing;
    private Boolean consentToTermsOfUser;
}

package project.seatsence.src.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserSignUpService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "01. [User]")
@Validated
@RequiredArgsConstructor
public class UserApi {

    private final UserSignUpService userSignUpService;

    @Operation(summary = "이메일 검증 및 중복 확인")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        return userSignUpService.isEmailDuplicated(validateEmailRequest.getEmail());
    }

    @Operation(summary = "닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameReq) {
        return userSignUpService.isNicknameDuplicated(validateNicknameReq.getNickname());
    }


}

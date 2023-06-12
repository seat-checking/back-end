package project.seatsence.src.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.src.user.dto.request.UserSignUpRequest;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserSignUpService;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "01. [User]")
@Validated
@RequiredArgsConstructor
public class UserApi {

    private final UserSignUpService userSignUpService;

    @Operation(summary = "이메일 검증 및 중복 확인")
    @GetMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        return userSignUpService.isEmailDuplicated(validateEmailRequest.getEmail());
    }

    @Operation(summary = "닉네임 검증 및 중복 확인")
    @GetMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        return userSignUpService.isNicknameDuplicated(validateNicknameRequest.getNickname());
    }

    @Operation(summary = "유저 회원가입")
    @PostMapping("/sign-up")
    public void userSignUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
        userSignUpService.userSignUp(userSignUpRequest);
    }
}

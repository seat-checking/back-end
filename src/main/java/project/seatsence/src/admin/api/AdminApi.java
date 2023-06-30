package project.seatsence.src.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.admin.dto.request.AdminSignInRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.admin.service.AdminService;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserSignUpService;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "02. [Admin]")
public class AdminApi {
    private final AdminService adminService;
    private final UserSignUpService userSignUpService;

    @Operation(summary = "어드민 회원가입")
    @PostMapping("/sign-up")
    public void adminSignUp(@Valid @RequestBody AdminSignUpRequest adminSignUpRequest) {
        adminService.adminSignUp(adminSignUpRequest);
    }

    @Operation(summary = "어드민 회원가입 이메일 중복 검사")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userSignUpService.isUsableByEmailDuplicateCheck(
                                validateEmailRequest.getEmail()));
        return response;
    }

    @Operation(summary = "어드민 닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        ValidateUserInformationResponse response =
                new ValidateUserInformationResponse(
                        userSignUpService.isUsableByNicknameDuplicateCheck(
                                validateNicknameRequest.getNickname()));
        return response;
    }

    @Operation(summary = "어드민 로그인")
    @PostMapping("/sign-in")
    public void adminSignIn(@Valid @RequestBody AdminSignInRequest adminSignInRequest) {
        try {
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ResponseCode.INTERNAL_ERROR);
        }
    }

    @Operation(summary = "어드민 사업자정보 추가")
    @PostMapping("/new-business-information/{user-id}")
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            @PathVariable("user-id") Long userId,
            @Valid @RequestBody
                    AdminNewBusinessInformationRequest adminNewBusinessInformationRequest) {
        return adminService.adminNewBusinessInformation(userId, adminNewBusinessInformationRequest);
    }
}

package project.seatsence.src.admin.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.dto.request.AdminSignInRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.service.AdminSignUpService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.dto.request.ValidateEmailRequest;
import project.seatsence.src.user.dto.request.ValidateNicknameRequest;
import project.seatsence.src.user.dto.response.ValidateUserInformationResponse;
import project.seatsence.src.user.service.UserSignUpService;

@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "03. [Admin]")
public class AdminApi {
    private final AdminSignUpService adminSignUpService;
    private final UserSignUpService userSignUpService;

    @Operation(summary = "어드민 회원가입")
    @PostMapping("/sign-up")
    public void adminSignUp(@Valid @RequestBody AdminSignUpRequest adminSignUpRequest) {

        try {
            User newAdmin =
                    new User(
                            adminSignUpRequest.getEmail(),
                            adminSignUpRequest.getPassword(),
                            UserRole.ADMIN,
                            adminSignUpRequest.getEmployerIdNumber(),
                            adminSignUpRequest.getAge(),
                            adminSignUpRequest.getNickname(),
                            adminSignUpRequest.getSex(),
                            adminSignUpRequest.getConsentToMarketing(),
                            adminSignUpRequest.getConsentToTermsOfUser());

            LocalDate openDate =
                    LocalDate.parse(adminSignUpRequest.getOpenDate(), DateTimeFormatter.ISO_DATE);
            AdminInfo newAdminInfo =
                    new AdminInfo(
                            newAdmin,
                            adminSignUpRequest.getEmployerIdNumber(),
                            openDate,
                            adminSignUpRequest.getAdminName());

            adminSignUpService.checkPassword(adminSignUpRequest);

            adminSignUpService.save(newAdmin);
            adminSignUpService.save(newAdminInfo);

        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BaseException(ResponseCode.INTERNAL_ERROR);
        }
    }
    // TODO email validator랑 password
    // TODO admin_info 테이블에 따로 빼기

    @Operation(summary = "어드민 회원가입 이메일 중복 검사")
    @PostMapping("/validate/email")
    public ValidateUserInformationResponse validateEmail(
            @Valid @RequestBody ValidateEmailRequest validateEmailRequest) {
        return userSignUpService.isEmailDuplicated(validateEmailRequest.getEmail());
    }

    @Operation(summary = "어드민 닉네임 검증 및 중복 확인")
    @PostMapping("/validate/nickname")
    public ValidateUserInformationResponse validateNickname(
            @Valid @RequestBody ValidateNicknameRequest validateNicknameRequest) {
        return userSignUpService.isNicknameDuplicated(validateNicknameRequest.getNickname());
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
}

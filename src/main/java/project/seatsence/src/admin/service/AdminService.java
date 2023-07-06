package project.seatsence.src.admin.service;

import static project.seatsence.global.code.ResponseCode.USER_EMAIL_ALREADY_EXIST;
import static project.seatsence.global.code.ResponseCode.USER_NICKNAME_ALREADY_EXIST;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.store.dao.StoreMemberAuthorityRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreMemberAuthorityRepository storeMemberAuthorityRepository;

    public Boolean checkDuplicatedEmail(String email) {
        return !adminRepository.existsByEmailAndState(email, ACTIVE);
    }

    public Boolean checkDuplicatedNickname(String nickname) {
        return !adminRepository.existsByNicknameAndState(nickname, ACTIVE);
    }

    public void checkPassword(AdminSignUpRequest adminSignUpRequest) {
        if (!adminSignUpRequest.getPassword().equals(adminSignUpRequest.getPasswordChecked())) {
            throw new BaseException(ResponseCode.USER_MISMATCHED_PASSWORD);
        }
    }

    public User findById(Long userId) {
        return adminRepository
                .findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }

    public void adminSignUp(AdminSignUpRequest adminSignUpRequest) {
        User newAdmin =
                new User(
                        adminSignUpRequest.getEmail(),
                        passwordEncoder.encode(adminSignUpRequest.getPassword()),
                        UserRole.ADMIN,
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
                        adminSignUpRequest.getBusinessRegistrationNumber(),
                        openDate,
                        adminSignUpRequest.getAdminName());

        checkPassword(adminSignUpRequest);

        if (!checkDuplicatedEmail(adminSignUpRequest.getEmail())) {
            throw new BaseException(USER_EMAIL_ALREADY_EXIST);
        }
        if (!checkDuplicatedNickname(adminSignUpRequest.getNickname())) {
            throw new BaseException(USER_NICKNAME_ALREADY_EXIST);
        }
        adminRepository.save(newAdmin);
        adminInfoRepository.save(newAdminInfo);
    }

    // 사업자 등록번호 추가
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            Long id, AdminNewBusinessInformationRequest newBusinessInformationRequest) {
        User user = findById(id);
        LocalDate openDate =
                LocalDate.parse(
                        newBusinessInformationRequest.getOpenDate(), DateTimeFormatter.ISO_DATE);
        AdminInfo newAdminInfo =
                new AdminInfo(
                        user,
                        newBusinessInformationRequest.getBusinessRegistrationNumber(),
                        openDate,
                        newBusinessInformationRequest.getAdminName());

        adminInfoRepository.save(newAdminInfo);
        return new AdminNewBusinessInformationResponse(newAdminInfo.getId());
    }
}

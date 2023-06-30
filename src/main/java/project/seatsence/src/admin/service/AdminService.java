package project.seatsence.src.admin.service;

import static project.seatsence.global.code.ResponseCode.USER_EMAIL_ALREADY_EXIST;
import static project.seatsence.global.code.ResponseCode.USER_NICKNAME_ALREADY_EXIST;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.dto.request.AdminNewBusinessRegistrationNumberRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.dto.response.AdminNewBusinessRegistrationNumberResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;

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

    public User findById(Long id) {
        return adminRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }

    public void adminSignUp(AdminSignUpRequest adminSignUpRequest) {
        User newAdmin =
                new User(
                        adminSignUpRequest.getEmail(),
                        adminSignUpRequest.getPassword(),
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
    public AdminNewBusinessRegistrationNumberResponse adminNewBusinessRegistrationNumber(
            Long id,
            AdminNewBusinessRegistrationNumberRequest adminNewBusinessRegistrationNumberRequest) {
        User user = findById(id);
        LocalDate openDate =
                LocalDate.parse(
                        adminNewBusinessRegistrationNumberRequest.getOpenDate(),
                        DateTimeFormatter.ISO_DATE);
        AdminInfo newAdminInfo =
                new AdminInfo(
                        user,
                        adminNewBusinessRegistrationNumberRequest.getBusinessRegistrationNumber(),
                        openDate,
                        adminNewBusinessRegistrationNumberRequest.getAdminName());

        adminInfoRepository.save(newAdminInfo);

        return new AdminNewBusinessRegistrationNumberResponse(newAdminInfo.getId());
    }
}

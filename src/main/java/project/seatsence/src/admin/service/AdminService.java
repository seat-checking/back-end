package project.seatsence.src.admin.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.config.security.JwtProvider;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.admin.dto.request.AdminSignInRequest;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.admin.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;
import project.seatsence.src.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreMemberRepository storeMemberRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;

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

    public AdminInfo findAdminInfoById(Long adminInfoId) {
        return adminInfoRepository
                .findById(adminInfoId)
                .orElseThrow(() -> new BaseException(ResponseCode.ADMIN_INFO_NOT_FOUND));
    }

    public List<AdminInfo> findAllByUserId(Long userId) {
        return adminInfoRepository.findAllByUserId(userId);
    }

    public void adminSignUp(AdminSignUpRequest adminSignUpRequest) {
        User newAdmin =
                new User(
                        adminSignUpRequest.getEmail(),
                        passwordEncoder.encode(adminSignUpRequest.getPassword()),
                        UserRole.ADMIN,
                        adminSignUpRequest.getName(),
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

    public User findAdmin(AdminSignInRequest adminSignInRequest) {
        User user = userService.findUserByUserEmail(adminSignInRequest.getEmail());

        UserRole userRole = user.getRole();

        if (!(userRole.equals(UserRole.ADMIN)
                || storeMemberRepository.existsByUserIdAndState(user.getId(), ACTIVE))) {
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(adminSignInRequest.getPassword(), user.getPassword())) {
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }

        return user;
    }

    public void adminSignIn(HttpServletResponse response, String refreshToken) {

        Cookie cookie = jwtProvider.createCookie(refreshToken);
        response.addCookie(cookie);
    }

    public StoreMember checkStorePosition(User user) {
        StoreMember storeMember =
                storeMemberRepository.findByUserId(user.getId()).orElseGet(() -> createTempOwner());
        return storeMember;
    }

    public StoreMember createTempOwner() {
        StoreMember storeMember = new StoreMember();
        storeMember.setPosition(StorePosition.OWNER);
        storeMember.setPermissionByMenu(
                "{\"storeStatus\":true,\"seatSetting\":true,\"storeStatistics\":true,\"storeSetting\":true}");
        return storeMember;
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

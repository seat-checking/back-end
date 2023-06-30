package project.seatsence.src.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.dto.Response.AdminSignInResponse;
import project.seatsence.src.admin.dto.request.AdminSignInRequest;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.domain.UserRole;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminSignInService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    //TODO Response 반환
    //TODO Token 적용

    public String adminSignIn(AdminSignInRequest adminSignInRequest) {
        User user = adminRepository.findByEmailAndState(adminSignInRequest.getEmail(), BaseTimeAndStateEntity.State.ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));

        //TODO 멤버 확인
        if(!passwordEncoder.matches(adminSignInRequest.getPassword(),user.getPassword())){
            throw new BaseException(ResponseCode.USER_NOT_FOUND);
        }

        //어드민민
//       return JwtTokenUtil.createToken(userId, secretKey, expiredTimeMs);
        return "토큰 로그인";
    }
}

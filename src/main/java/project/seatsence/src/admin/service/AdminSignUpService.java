package project.seatsence.src.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.dto.request.AdminSignUpRequest;
import project.seatsence.src.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminSignUpService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;

    public void save(User user) {
        adminRepository.save(user);
    }

    public void save(AdminInfo adminInfo) {
        adminInfoRepository.save(adminInfo);
    }

    public void checkPassword(AdminSignUpRequest adminSignUpRequest) {

        if (!adminSignUpRequest.getPassword().equals(adminSignUpRequest.getPasswordChecked())) {
            throw new BaseException(ResponseCode.INVALID_FIELD_VALUE);
        }
    }
}

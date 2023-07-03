package project.seatsence.src.admin.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.dao.AdminMemberAuthorityRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminAuthority;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.domain.AdminMemberAuthority;
import project.seatsence.src.admin.dto.request.AdminMemberRegistrationRequest;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;
    private final StoreRepository storeRepository;
    private final AdminMemberAuthorityRepository adminMemberAuthorityRepository;

    public User findByEmail(String email) {
        return adminRepository
                .findByEmailAndState(email, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }

    public void adminMemberRegistration(
            Long storeId, AdminMemberRegistrationRequest adminMemberRegistrationRequest) {

        User user = findByEmail(adminMemberRegistrationRequest.getEmail());

        // TODO 지금 store말고 user 가져왔음
        AdminInfo adminInfo =
                adminInfoRepository
                        .findByUserId(user.getId())
                        .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));

        // TODO json to string
        String permissionByMenu = "권한";

        AdminMemberAuthority newAdminMemberAuthority =
                new AdminMemberAuthority(
                        adminInfo, user, store, AdminAuthority.MEMBER, permissionByMenu);
        adminMemberAuthorityRepository.save(newAdminMemberAuthority);
    }
}

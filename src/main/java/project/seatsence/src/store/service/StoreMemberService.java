package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.store.dao.StoreMemberAuthorityRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.store.domain.StoreAuthority;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.store.domain.StoreMemberAuthority;
import project.seatsence.src.store.dto.request.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreMemberService {

    private final AdminRepository adminRepository;
    private final AdminInfoRepository adminInfoRepository;
    private final StoreRepository storeRepository;
    private final StoreMemberAuthorityRepository storeMemberAuthorityRepository;

    public User findByEmail(String email) {
        return adminRepository
                .findByEmailAndState(email, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.USER_NOT_FOUND));
    }

    public void storeMemberRegistration(
            Long storeId, StoreMemberRegistrationRequest storeMemberRegistrationRequest) {

        User user = findByEmail(storeMemberRegistrationRequest.getEmail());

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

        StoreMemberAuthority newStoreMemberAuthority =
                new StoreMemberAuthority(
                        adminInfo, user, store, StoreAuthority.MEMBER, permissionByMenu);
        storeMemberAuthorityRepository.save(newStoreMemberAuthority);
    }
}

package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.INACTIVE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.code.ResponseCode;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.admin.dao.AdminInfoRepository;
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.store.dao.StoreMemberAuthorityRepository;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreAuthority;
import project.seatsence.src.store.domain.StoreMemberAuthority;
import project.seatsence.src.store.dto.request.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.request.StoreMemberUpdateRequest;
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

    public StoreMemberAuthority findById(Long id) {
        return storeMemberAuthorityRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.STORE_MEMBER_NOT_FOUND));
    }

    public Boolean memberExists(User user) {
        return !storeMemberAuthorityRepository.existsByUserIdAndState(user.getId(), ACTIVE);
    }

    public void storeMemberRegistration(
            Long storeId, StoreMemberRegistrationRequest storeMemberRegistrationRequest)
            throws JsonProcessingException {

        User user = findByEmail(storeMemberRegistrationRequest.getEmail());

        if (!memberExists(user)) {
            throw new BaseException(ResponseCode.STORE_MEMBER_ALREADY_EXIST);
        }

        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));

        AdminInfo adminInfo = store.getAdminInfo();

        // json to string
        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(
                        storeMemberRegistrationRequest.getPermissionByMenu());

        StoreMemberAuthority newStoreMemberAuthority =
                new StoreMemberAuthority(
                        adminInfo, user, store, StoreAuthority.MEMBER, permissionByMenu);
        storeMemberAuthorityRepository.save(newStoreMemberAuthority);
    }

    //    public StoreMemberListResponse findByStoreId(Long id){
    //        List<StoreMemberAuthority> storeMembers=storeMemberAuthorityRepository
    //                .findAllByStoreIdAndState(id, ACTIVE);
    //
    //        return StoreMemberListResponse(storeMembers);
    //    }
    public List<StoreMemberAuthority> findAllByStoreId(Long id) {
        return storeMemberAuthorityRepository.findAllByStoreIdAndState(id, ACTIVE);
    }

    public void update(Long storeId, StoreMemberUpdateRequest storeMemberUpdateRequest)
            throws JsonProcessingException {

        StoreMemberAuthority storeMemberAuthority = findById(storeMemberUpdateRequest.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(storeMemberUpdateRequest.getPermissionByMenu());
        storeMemberAuthority.setPermissionByMenu(permissionByMenu);
    }

    public void delete(Long id) {
        StoreMemberAuthority storeMemberAuthority = findById(id);
        System.out.println("==============");
        System.out.println(
                "storeMemberAuthority.getPermissionByMenu() = "
                        + storeMemberAuthority.getPermissionByMenu());
        storeMemberAuthority.setState(INACTIVE);
    }
}

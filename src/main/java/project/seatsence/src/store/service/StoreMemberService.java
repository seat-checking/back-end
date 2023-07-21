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
import project.seatsence.src.admin.dao.AdminRepository;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.store.dto.request.StoreMemberRegistrationRequest;
import project.seatsence.src.store.dto.request.StoreMemberUpdateRequest;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserAdaptor;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreMemberService {

    private final AdminRepository adminRepository;
    private final StoreRepository storeRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final UserAdaptor userAdaptor;

    public StoreMember findById(Long id) {
        return storeMemberRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(ResponseCode.STORE_MEMBER_NOT_FOUND));
    }

    public StoreMember findByUserId(Long userId) {
        return storeMemberRepository
                .findByUserId(userId)
                .orElseThrow(() -> new BaseException(ResponseCode.STORE_MEMBER_NOT_FOUND));
    }

    public Boolean memberExists(User user) {
        return !storeMemberRepository.existsByUserIdAndState(user.getId(), ACTIVE);
    }

    public User findUserByEmail(String email) {
        User user = userAdaptor.findByEmail(email);

        if (!memberExists(user)) {
            throw new BaseException(ResponseCode.STORE_MEMBER_ALREADY_EXIST);
        }

        return user;
    }

    public void storeMemberRegistration(
            Long storeId, StoreMemberRegistrationRequest storeMemberRegistrationRequest)
            throws JsonProcessingException {

        User user = userAdaptor.findByEmail(storeMemberRegistrationRequest.getEmail());

        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));

        AdminInfo adminInfo = store.getAdminInfo();

        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(
                        storeMemberRegistrationRequest.getPermissionByMenu());

        StoreMember newStoreMember =
                new StoreMember(adminInfo, user, store, StorePosition.MEMBER, permissionByMenu);
        storeMemberRepository.save(newStoreMember);
    }

    public List<StoreMember> findAllByStoreIdAndPosition(Long id) {

        List<StoreMember> memberList =
                storeMemberRepository.findAllByStoreIdAndPositionAndState(
                        id, StorePosition.MEMBER, ACTIVE);
        if (memberList == null || memberList.isEmpty())
            throw new BaseException(ResponseCode.STORE_MEMBER_NOT_FOUND);
        return memberList;
    }

    public void update(Long storeId, StoreMemberUpdateRequest storeMemberUpdateRequest)
            throws JsonProcessingException {

        StoreMember storeMember = findById(storeMemberUpdateRequest.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String permissionByMenu =
                objectMapper.writeValueAsString(storeMemberUpdateRequest.getPermissionByMenu());
        storeMember.setPermissionByMenu(permissionByMenu);
    }

    public void delete(Long id) {
        StoreMember storeMember = findById(id);
        storeMember.setState(INACTIVE);
    }
}

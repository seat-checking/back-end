package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;
import project.seatsence.src.store.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.store.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final UserService userService;
    private final StoreRepository storeRepository;
    private final StoreMemberRepository storeMemberRepository;

    public Store findById(Long id) {
        return storeRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
    }

    // 사업자 등록번호 추가
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            Long id, AdminNewBusinessInformationRequest newBusinessInformationRequest) {
        User user = userService.findByIdAndState(id);
        LocalDate openDate =
                LocalDate.parse(
                        newBusinessInformationRequest.getOpenDate(), DateTimeFormatter.ISO_DATE);
        Store newStore =
                new Store(
                        user,
                        newBusinessInformationRequest.getBusinessRegistrationNumber(),
                        openDate,
                        newBusinessInformationRequest.getAdminName(),
                        newBusinessInformationRequest.getStoreName());

        // OWNER 권한
        StoreMember newStoreMember =
                StoreMember.builder()
                        .user(user)
                        .store(newStore)
                        .position(StorePosition.OWNER)
                        .permissionByMenu(
                                "{\"STORE_STATUS\" :true, \"SEAT_SETTING\" : true, \"STORE_STATISTICS\" : true, \"STORE_SETTING\" : true}")
                        .build();

        storeRepository.save(newStore);
        storeMemberRepository.save(newStoreMember);

        return new AdminNewBusinessInformationResponse(newStore.getId());
    }
}

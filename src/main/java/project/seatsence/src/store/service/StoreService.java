package project.seatsence.src.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.seatsence.src.store.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final UserService userService;
    private final StoreRepository storeRepository;

    // 사업자 등록번호 추가
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            Long id, AdminNewBusinessInformationRequest newBusinessInformationRequest) {
        User user = userService.findById(id);
        LocalDate openDate =
                LocalDate.parse(
                        newBusinessInformationRequest.getOpenDate(), DateTimeFormatter.ISO_DATE);
        Store newStore = new Store(
                user,
                newBusinessInformationRequest.getBusinessRegistrationNumber(),
                openDate,
                newBusinessInformationRequest.getAdminName(),
                newBusinessInformationRequest.getStoreName());

//        AdminInfo newAdminInfo =
//                new AdminInfo(
//                        user,
//                        newBusinessInformationRequest.getBusinessRegistrationNumber(),
//                        openDate,
//                        newBusinessInformationRequest.getAdminName());

        storeRepository.save(newStore);
        return new AdminNewBusinessInformationResponse(newStore.getId());
    }
}

package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.util.EnumUtils;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreWifi;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.response.AdminStoreResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreWifiRepository storeWifiRepository;
    private final StoreMapper storeMapper;

    public AdminStoreResponse findById(Long id) {
        Store store =
                storeRepository
                        .findByIdAndState(id, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        return storeMapper.toDto(store);
    }

    @Transactional
    public void save(AdminStoreCreateRequest adminStoreCreateRequest) {
        Store newStore = new Store();
        newStore.setName(adminStoreCreateRequest.getName());
        newStore.setIntroduction(adminStoreCreateRequest.getIntroduction());
        newStore.setLocation(adminStoreCreateRequest.getLocation());
        newStore.setTotalFloor(adminStoreCreateRequest.getTotalFloor());
        newStore.setCategory(
                EnumUtils.getEnumFromString(adminStoreCreateRequest.getCategory(), Category.class));
        newStore.setDayOff(EnumUtils.getStringFromEnumList(adminStoreCreateRequest.getDayOff()));
        newStore.setMonOpenTime(adminStoreCreateRequest.getMonOpenTime());
        newStore.setMonCloseTime(adminStoreCreateRequest.getMonCloseTime());
        newStore.setTueOpenTime(adminStoreCreateRequest.getTueOpenTime());
        newStore.setTueCloseTime(adminStoreCreateRequest.getTueCloseTime());
        newStore.setWedOpenTime(adminStoreCreateRequest.getWedOpenTime());
        newStore.setWedCloseTime(adminStoreCreateRequest.getWedCloseTime());
        newStore.setThuOpenTime(adminStoreCreateRequest.getThuOpenTime());
        newStore.setThuCloseTime(adminStoreCreateRequest.getThuCloseTime());
        newStore.setFriOpenTime(adminStoreCreateRequest.getFriOpenTime());
        newStore.setFriCloseTime(adminStoreCreateRequest.getFriCloseTime());
        newStore.setSatOpenTime(adminStoreCreateRequest.getSatOpenTime());
        newStore.setSatCloseTime(adminStoreCreateRequest.getSatCloseTime());
        newStore.setSunOpenTime(adminStoreCreateRequest.getSunOpenTime());
        newStore.setSunCloseTime(adminStoreCreateRequest.getSunCloseTime());
        newStore.setBreakTime(adminStoreCreateRequest.getBreakTime());
        newStore.setUseTimeLimit(adminStoreCreateRequest.getUseTimeLimit());
        // wifi 정보 저장
        List<String> wifi = adminStoreCreateRequest.getWifi();
        for (String w : wifi) {
            StoreWifi storeWifi = new StoreWifi();
            storeWifi.setWifi(w);
            storeWifi.setStore(newStore);
            storeWifiRepository.save(storeWifi);
        }
        storeRepository.save(newStore);
    }
}

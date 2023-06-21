package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;

import java.util.*;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreWifi;
import project.seatsence.src.store.dto.StoreMapper;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
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
            StoreWifi newStoreWifi = new StoreWifi();
            newStoreWifi.setWifi(w);
            newStoreWifi.setStore(newStore);
            StoreWifi storeWifi = storeWifiRepository.save(newStoreWifi);
            newStore.getWifiList().add(storeWifi);
        }
        storeRepository.save(newStore);
    }

    @Transactional
    public void update(Long id, AdminStoreUpdateRequest adminStoreUpdateRequest) {
        Store store =
                storeRepository
                        .findByIdAndState(id, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        store.setName(adminStoreUpdateRequest.getName());
        store.setIntroduction(adminStoreUpdateRequest.getIntroduction());
        store.setLocation(adminStoreUpdateRequest.getLocation());
        store.setTotalFloor(adminStoreUpdateRequest.getTotalFloor());
        store.setCategory(
                EnumUtils.getEnumFromString(adminStoreUpdateRequest.getCategory(), Category.class));
        store.setDayOff(EnumUtils.getStringFromEnumList(adminStoreUpdateRequest.getDayOff()));
        store.setMonOpenTime(adminStoreUpdateRequest.getMonOpenTime());
        store.setMonCloseTime(adminStoreUpdateRequest.getMonCloseTime());
        store.setTueOpenTime(adminStoreUpdateRequest.getTueOpenTime());
        store.setTueCloseTime(adminStoreUpdateRequest.getTueCloseTime());
        store.setWedOpenTime(adminStoreUpdateRequest.getWedOpenTime());
        store.setWedCloseTime(adminStoreUpdateRequest.getWedCloseTime());
        store.setThuOpenTime(adminStoreUpdateRequest.getThuOpenTime());
        store.setThuCloseTime(adminStoreUpdateRequest.getThuCloseTime());
        store.setFriOpenTime(adminStoreUpdateRequest.getFriOpenTime());
        store.setFriCloseTime(adminStoreUpdateRequest.getFriCloseTime());
        store.setSatOpenTime(adminStoreUpdateRequest.getSatOpenTime());
        store.setSatCloseTime(adminStoreUpdateRequest.getSatCloseTime());
        store.setSunOpenTime(adminStoreUpdateRequest.getSunOpenTime());
        store.setSunCloseTime(adminStoreUpdateRequest.getSunCloseTime());
        store.setBreakTime(adminStoreUpdateRequest.getBreakTime());
        store.setUseTimeLimit(adminStoreUpdateRequest.getUseTimeLimit());

        List<StoreWifi> existWifiList = store.getWifiList();
        ArrayList<Boolean> existWifiChkList = new ArrayList<>();
        List<String> newWifiList = adminStoreUpdateRequest.getWifi();
        ArrayList<Boolean> newWifiChkList = new ArrayList<>();

        // 기존에 존재하는 와이파이 check
        for (int i = 0; i < existWifiList.size(); i++) {
            existWifiChkList.add(false);
        }

        // 새로 들어온 와이파이 check
        for (int i = 0; i < newWifiList.size(); i++) {
            newWifiChkList.add(true);
        }

        // 기존에 존재하는 와이파이 정보 중 없어진 정보 탐색
        // 새로 들어온 와이파이 정보 탐색
        for (int i = 0; i < existWifiList.size(); i++) {
            StoreWifi existWifi = existWifiList.get(i);
            for (String newWifi : newWifiList) {
                if (newWifi.equals(existWifi.getWifi())) {
                    newWifiChkList.set(i, false); // 새로 들어온 와이파이가 아님
                    existWifiChkList.set(i, true); // 기존에 있던 와이파이임
                }
            }
        }

        // 기존 와이파이 중 없어진 정보
        for (int i = 0; i < existWifiChkList.size(); i++) {
            if (!existWifiChkList.get(i)) {
                StoreWifi storeWifi = existWifiList.get(i);
                store.getWifiList().remove(storeWifi);
            }
        }

        // 새로 들어온 와이파이 정보
        for (int i = 0; i < newWifiChkList.size(); i++) {
            if (newWifiChkList.get(i)) {
                StoreWifi storeWifi = new StoreWifi();
                storeWifi.setWifi(newWifiList.get(i));
                storeWifi.setStore(store);
                store.getWifiList().add(storeWifi);
                storeWifiRepository.save(storeWifi);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        Store store =
                storeRepository
                        .findByIdAndState(id, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        store.setState(INACTIVE);
    }
}

package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.store.domain.Day.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.admin.domain.AdminInfo;
import project.seatsence.src.admin.service.AdminAdapter;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserAdaptor;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreWifiRepository storeWifiRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final AdminAdapter adminAdapter;
    private final UserAdaptor userAdaptor;

    public Page<Store> findAll(String category, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                Store.class.getDeclaredField(sortField);
            }
            if (category == null) {
                return storeRepository.findAllByState(ACTIVE, pageable);
            } else {
                Category categoryEnum = EnumUtils.getEnumFromString(category, Category.class);
                return storeRepository.findAllByStateAndCategory(ACTIVE, categoryEnum, pageable);
            }
        } catch (NoSuchFieldException e) {
            throw new BaseException(STORE_SORT_FIELD_NOT_FOUND);
        }
    }

    public Store findById(Long id) {
        return storeRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
    }

    public List<Store> findAllOwnedStore(Long userId) {
        List<AdminInfo> adminInfoList = adminAdapter.findAllByUserId(userId);
        return storeRepository.findAllByAdminInfoIdIn(
                adminInfoList.stream().map(AdminInfo::getId).collect(Collectors.toList()));
    }

    public Page<Store> findAllByName(String name, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                Store.class.getDeclaredField(sortField);
            }

            List<Store> nameList = storeRepository.findALlByStateAndNameOrderByIdAsc(ACTIVE, name);
            List<Store> containNameList =
                    storeRepository.findAllByStateAndNameContainingIgnoreCaseOrderByIdAsc(
                            ACTIVE, name);
            nameList.addAll(containNameList);

            List<Store> nameDistinctList =
                    nameList.stream().distinct().collect(Collectors.toList());

            // make nameList to page
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), nameDistinctList.size());
            return new PageImpl<>(
                    nameDistinctList.subList(start, end), pageable, nameDistinctList.size());

        } catch (NoSuchFieldException e) {
            throw new BaseException(STORE_SORT_FIELD_NOT_FOUND);
        }
    }

    @Transactional
    public void save(AdminStoreCreateRequest adminStoreCreateRequest, String userEmail) {
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
        // 가게에 연결된 사업자 정보 등록
        AdminInfo adminInfo = adminAdapter.findById(adminStoreCreateRequest.getAdminInfoId());
        newStore.setAdminInfo(adminInfo);
        // store member 정보 저장
        User user = userAdaptor.findByEmail(userEmail);
        StoreMember newStoreMember =
                StoreMember.builder()
                        .adminInfo(adminInfo)
                        .user(user)
                        .store(newStore)
                        .position(StorePosition.OWNER)
                        .permissionByMenu(
                                "{\"STORE_STATUS\" :true, \"SEAT_SETTING\" : true, \"STORE_STATISTICS\" : true, \"STORE_SETTING\" : true}")
                        .build();
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
        storeMemberRepository.save(newStoreMember);
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

    public boolean isOpen(Store store) {
        // today's day of week
        Calendar cal = Calendar.getInstance();
        int todayDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        log.info(String.valueOf(todayDayOfWeek)); // 오늘 요일
        String dayOff = store.getDayOff();
        List<Day> dayOffList = EnumUtils.getEnumListFromString(dayOff, Day.class);
        for (Day day : dayOffList) {
            // 휴무일일 경우
            if (todayDayOfWeek == day.getDayOfWeek()) {
                return false;
            }
        }
        // today's time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // get only hour and minute from currentTime
        LocalTime currentTime = LocalTime.now();
        // get openTime and closeTime from store
        LocalTime openTime;
        LocalTime closeTime;
        LocalTime breakStartTime;
        LocalTime breakEndTime;
        String breakTime = store.getBreakTime();
        String[] breakTimeList = breakTime.split("~");
        breakStartTime = LocalTime.parse(breakTimeList[0], formatter);
        breakEndTime = LocalTime.parse(breakTimeList[1], formatter);
        if (todayDayOfWeek == MON.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getMonOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getMonCloseTime(), formatter);
        } else if (todayDayOfWeek == TUE.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getTueOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getTueCloseTime(), formatter);
        } else if (todayDayOfWeek == WED.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getWedOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getWedCloseTime(), formatter);
        } else if (todayDayOfWeek == THU.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getThuOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getThuCloseTime(), formatter);
        } else if (todayDayOfWeek == FRI.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getFriOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getFriCloseTime(), formatter);
        } else if (todayDayOfWeek == SAT.getDayOfWeek()) {
            openTime = LocalTime.parse(store.getSatOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getSatCloseTime(), formatter);
        } else {
            openTime = LocalTime.parse(store.getSunOpenTime(), formatter);
            closeTime = LocalTime.parse(store.getSunCloseTime(), formatter);
        }
        // check if currentTime is between openTime and closeTime
        return currentTime.isAfter(openTime)
                && currentTime.isBefore(closeTime)
                && (currentTime.isBefore(breakStartTime) || currentTime.isAfter(breakEndTime));
    }
}

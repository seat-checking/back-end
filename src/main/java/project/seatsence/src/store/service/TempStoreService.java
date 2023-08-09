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
import project.seatsence.src.admin.service.AdminService;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.TempStoreRepository;
import project.seatsence.src.store.dao.StoreWifiRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminStoreCreateRequest;
import project.seatsence.src.store.dto.request.AdminStoreUpdateRequest;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class TempStoreService {
    private final TempStoreRepository tempStoreRepository;
    private final StoreWifiRepository storeWifiRepository;
    private final StoreMemberRepository storeMemberRepository;
    private final UserService userService;
    private final AdminService adminService;

    public Page<TempStore> findAll(String category, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                TempStore.class.getDeclaredField(sortField);
            }
            if (category == null) {
                return tempStoreRepository.findAllByState(ACTIVE, pageable);
            } else {
                Category categoryEnum = EnumUtils.getEnumFromString(category, Category.class);
                return tempStoreRepository.findAllByStateAndCategory(ACTIVE, categoryEnum, pageable);
            }
        } catch (NoSuchFieldException e) {
            throw new BaseException(STORE_SORT_FIELD_NOT_FOUND);
        }
    }

    public TempStore findById(Long id) {
        return tempStoreRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
    }

    public List<TempStore> findAllOwnedStore(String userEmail) {
        User adminUser = adminService.findByEmail(userEmail);
        List<AdminInfo> adminInfoList = adminService.findAllByUserId(adminUser.getId());
        return tempStoreRepository.findAllByAdminInfoIdIn(
                adminInfoList.stream().map(AdminInfo::getId).collect(Collectors.toList()));
    }

    public Page<TempStore> findAllByName(String name, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                TempStore.class.getDeclaredField(sortField);
            }

            List<TempStore> nameList = tempStoreRepository.findALlByStateAndNameOrderByIdAsc(ACTIVE, name);
            List<TempStore> containNameList =
                    tempStoreRepository.findAllByStateAndNameContainingIgnoreCaseOrderByIdAsc(
                            ACTIVE, name);
            nameList.addAll(containNameList);

            List<TempStore> nameDistinctList =
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
        TempStore newTempStore = new TempStore();
        newTempStore.setName(adminStoreCreateRequest.getName());
        newTempStore.setIntroduction(adminStoreCreateRequest.getIntroduction());
        newTempStore.setLocation(adminStoreCreateRequest.getLocation());
        newTempStore.setTotalFloor(adminStoreCreateRequest.getTotalFloor());
        newTempStore.setCategory(
                EnumUtils.getEnumFromString(adminStoreCreateRequest.getCategory(), Category.class));
        newTempStore.setDayOff(EnumUtils.getStringFromEnumList(adminStoreCreateRequest.getDayOff()));
        newTempStore.setMonOpenTime(adminStoreCreateRequest.getMonOpenTime());
        newTempStore.setMonCloseTime(adminStoreCreateRequest.getMonCloseTime());
        newTempStore.setTueOpenTime(adminStoreCreateRequest.getTueOpenTime());
        newTempStore.setTueCloseTime(adminStoreCreateRequest.getTueCloseTime());
        newTempStore.setWedOpenTime(adminStoreCreateRequest.getWedOpenTime());
        newTempStore.setWedCloseTime(adminStoreCreateRequest.getWedCloseTime());
        newTempStore.setThuOpenTime(adminStoreCreateRequest.getThuOpenTime());
        newTempStore.setThuCloseTime(adminStoreCreateRequest.getThuCloseTime());
        newTempStore.setFriOpenTime(adminStoreCreateRequest.getFriOpenTime());
        newTempStore.setFriCloseTime(adminStoreCreateRequest.getFriCloseTime());
        newTempStore.setSatOpenTime(adminStoreCreateRequest.getSatOpenTime());
        newTempStore.setSatCloseTime(adminStoreCreateRequest.getSatCloseTime());
        newTempStore.setSunOpenTime(adminStoreCreateRequest.getSunOpenTime());
        newTempStore.setSunCloseTime(adminStoreCreateRequest.getSunCloseTime());
        newTempStore.setBreakTime(adminStoreCreateRequest.getBreakTime());
        newTempStore.setUseTimeLimit(adminStoreCreateRequest.getUseTimeLimit());
        // 가게에 연결된 사업자 정보 등록
        AdminInfo adminInfo =
                adminService.findAdminInfoById(adminStoreCreateRequest.getAdminInfoId());
        newTempStore.setAdminInfo(adminInfo);
        // store member 정보 저장
//        User user = userService.findUserByUserEmail(userEmail);
//        StoreMember newStoreMember =
//                StoreMember.builder()
//                        .adminInfo(adminInfo)
//                        .user(user)
//                        .tempStore(newTempStore)
//                        .position(StorePosition.OWNER)
//                        .permissionByMenu(
//                                "{\"STORE_STATUS\" :true, \"SEAT_SETTING\" : true, \"STORE_STATISTICS\" : true, \"STORE_SETTING\" : true}")
//                        .build();
        // wifi 정보 저장
        List<String> wifi = adminStoreCreateRequest.getWifi();
        for (String w : wifi) {
            StoreWifi newStoreWifi = new StoreWifi();
            newStoreWifi.setWifi(w);
            newStoreWifi.setTempStore(newTempStore);
            StoreWifi storeWifi = storeWifiRepository.save(newStoreWifi);
            newTempStore.getWifiList().add(storeWifi);
        }
        tempStoreRepository.save(newTempStore);
        //storeMemberRepository.save(newStoreMember);
    }

    @Transactional
    public void update(Long id, AdminStoreUpdateRequest adminStoreUpdateRequest) {
        TempStore tempStore = findById(id);

        tempStore.setName(adminStoreUpdateRequest.getName());
        tempStore.setIntroduction(adminStoreUpdateRequest.getIntroduction());
        tempStore.setLocation(adminStoreUpdateRequest.getLocation());
        tempStore.setTotalFloor(adminStoreUpdateRequest.getTotalFloor());
        tempStore.setCategory(
                EnumUtils.getEnumFromString(adminStoreUpdateRequest.getCategory(), Category.class));
        tempStore.setDayOff(EnumUtils.getStringFromEnumList(adminStoreUpdateRequest.getDayOff()));
        tempStore.setMonOpenTime(adminStoreUpdateRequest.getMonOpenTime());
        tempStore.setMonCloseTime(adminStoreUpdateRequest.getMonCloseTime());
        tempStore.setTueOpenTime(adminStoreUpdateRequest.getTueOpenTime());
        tempStore.setTueCloseTime(adminStoreUpdateRequest.getTueCloseTime());
        tempStore.setWedOpenTime(adminStoreUpdateRequest.getWedOpenTime());
        tempStore.setWedCloseTime(adminStoreUpdateRequest.getWedCloseTime());
        tempStore.setThuOpenTime(adminStoreUpdateRequest.getThuOpenTime());
        tempStore.setThuCloseTime(adminStoreUpdateRequest.getThuCloseTime());
        tempStore.setFriOpenTime(adminStoreUpdateRequest.getFriOpenTime());
        tempStore.setFriCloseTime(adminStoreUpdateRequest.getFriCloseTime());
        tempStore.setSatOpenTime(adminStoreUpdateRequest.getSatOpenTime());
        tempStore.setSatCloseTime(adminStoreUpdateRequest.getSatCloseTime());
        tempStore.setSunOpenTime(adminStoreUpdateRequest.getSunOpenTime());
        tempStore.setSunCloseTime(adminStoreUpdateRequest.getSunCloseTime());
        tempStore.setBreakTime(adminStoreUpdateRequest.getBreakTime());
        tempStore.setUseTimeLimit(adminStoreUpdateRequest.getUseTimeLimit());

        List<StoreWifi> existWifiList = tempStore.getWifiList();
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
                tempStore.getWifiList().remove(storeWifi);
            }
        }

        // 새로 들어온 와이파이 정보
        for (int i = 0; i < newWifiChkList.size(); i++) {
            if (newWifiChkList.get(i)) {
                StoreWifi storeWifi = new StoreWifi();
                storeWifi.setWifi(newWifiList.get(i));
                storeWifi.setTempStore(tempStore);
                tempStore.getWifiList().add(storeWifi);
                storeWifiRepository.save(storeWifi);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        TempStore tempStore =
                tempStoreRepository
                        .findByIdAndState(id, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        tempStore.setState(INACTIVE);
    }

    public boolean isOpen(TempStore tempStore) {
        // today's day of week
        Calendar cal = Calendar.getInstance();
        int todayDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        log.info(String.valueOf(todayDayOfWeek)); // 오늘 요일
        String dayOff = tempStore.getDayOff();
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
        String breakTime = tempStore.getBreakTime();
        String[] breakTimeList = breakTime.split("~");
        breakStartTime = LocalTime.parse(breakTimeList[0], formatter);
        breakEndTime = LocalTime.parse(breakTimeList[1], formatter);
        if (todayDayOfWeek == MON.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getMonOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getMonCloseTime(), formatter);
        } else if (todayDayOfWeek == TUE.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getTueOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getTueCloseTime(), formatter);
        } else if (todayDayOfWeek == WED.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getWedOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getWedCloseTime(), formatter);
        } else if (todayDayOfWeek == THU.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getThuOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getThuCloseTime(), formatter);
        } else if (todayDayOfWeek == FRI.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getFriOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getFriCloseTime(), formatter);
        } else if (todayDayOfWeek == SAT.getDayOfWeek()) {
            openTime = LocalTime.parse(tempStore.getSatOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getSatCloseTime(), formatter);
        } else {
            openTime = LocalTime.parse(tempStore.getSunOpenTime(), formatter);
            closeTime = LocalTime.parse(tempStore.getSunCloseTime(), formatter);
        }
        // check if currentTime is between openTime and closeTime
        return currentTime.isAfter(openTime)
                && currentTime.isBefore(closeTime)
                && (currentTime.isBefore(breakStartTime) || currentTime.isAfter(breakEndTime));
    }
}

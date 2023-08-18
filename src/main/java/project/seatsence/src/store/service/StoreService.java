package project.seatsence.src.store.service;

import static project.seatsence.global.code.ResponseCode.STORE_NOT_FOUND;
import static project.seatsence.global.code.ResponseCode.STORE_SORT_FIELD_NOT_FOUND;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;
import static project.seatsence.src.store.domain.Day.*;
import static project.seatsence.src.store.domain.Day.SAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.utils.EnumUtils;
import project.seatsence.src.store.dao.StoreMemberRepository;
import project.seatsence.src.store.dao.StoreRepository;
import project.seatsence.src.store.domain.*;
import project.seatsence.src.store.dto.request.AdminNewBusinessInformationRequest;
import project.seatsence.src.store.dto.request.AdminStoreBasicInformationRequest;
import project.seatsence.src.store.dto.request.AdminStoreOperatingTimeRequest;
import project.seatsence.src.store.dto.request.AdminStoreTemporaryClosedRequest;
import project.seatsence.src.store.dto.response.AdminNewBusinessInformationResponse;
import project.seatsence.src.store.dto.response.AdminOwnedStoreResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    private final UserService userService;
    private final StoreRepository storeRepository;
    private final StoreMemberRepository storeMemberRepository;

    public AdminOwnedStoreResponse findAllOwnedStore(String userEmail) {
        User user = userService.findByEmailAndState(userEmail);
        List<StoreMember> storeMemberList =
                storeMemberRepository.findAllByUserAndState(user, ACTIVE);
        List<Long> storeIds =
                storeMemberList.stream()
                        .map(storeMember -> storeMember.getStore().getId())
                        .collect(Collectors.toList());
        List<Store> storeList = storeRepository.findAllByIdInAndState(storeIds, ACTIVE);
        List<AdminOwnedStoreResponse.StoreResponse> storeResponseList =
                storeList.stream()
                        .map(
                                store ->
                                        new AdminOwnedStoreResponse.StoreResponse(
                                                store.getId(),
                                                store.getStoreName(),
                                                store.getIntroduction(),
                                                store.getMainImage(),
                                                isOpenNow(store),
                                                isClosedToday(store)))
                        .collect(Collectors.toList());
        return new AdminOwnedStoreResponse(storeResponseList);
    }

    @Transactional
    public void updateBasicInformation(AdminStoreBasicInformationRequest request, Long storeId) {
        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        store.updateBasicInformation(request);
    }

    public Store findByIdAndState(Long id) {
        return storeRepository
                .findByIdAndState(id, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
    }

    // 사업자 등록번호 추가
    public AdminNewBusinessInformationResponse adminNewBusinessInformation(
            String userEmail, AdminNewBusinessInformationRequest newBusinessInformationRequest) {
        User user = userService.findByEmailAndState(userEmail);
        LocalDate openDate =
                LocalDate.parse(
                        newBusinessInformationRequest.getOpenDate(), DateTimeFormatter.ISO_DATE);
        Store newStore =
                new Store(
                        user,
                        newBusinessInformationRequest.getBusinessRegistrationNumber(),
                        openDate,
                        newBusinessInformationRequest.getAdminName(),
                        newBusinessInformationRequest.getStoreName(),
                        newBusinessInformationRequest.getAddress(),
                        newBusinessInformationRequest.getDetailAddress());

        // OWNER 권한
        StoreMember newStoreMember =
                StoreMember.builder()
                        .user(user)
                        .store(newStore)
                        .position(StorePosition.OWNER)
                        .permissionByMenu(
                                "{\"storeStatus\":true,\"seatSetting\":true,\"storeStatistics\":true,\"storeSetting\":true}")
                        .build();

        storeRepository.save(newStore);
        storeMemberRepository.save(newStoreMember);

        return new AdminNewBusinessInformationResponse(newStore.getId());
    }

    @Transactional
    public void updateOperatingTime(AdminStoreOperatingTimeRequest request, Long storeId) {
        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        store.updateOperatingTime(request);
    }

    @Transactional
    public void delete(Long storeId) {
        Store store =
                storeRepository
                        .findByIdAndState(storeId, ACTIVE)
                        .orElseThrow(() -> new BaseException(STORE_NOT_FOUND));
        store.delete();
    }

    public Page<Store> findAllByState(String category, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                Store.class.getDeclaredField(sortField);
            }
            if (category == null) {
                return storeRepository.findAllByState(ACTIVE, pageable);
            } else {
                Category categoryEnum = EnumUtils.getEnumFromString(category, Category.class);
                return storeRepository.findAllByCategoryAndState(categoryEnum, ACTIVE, pageable);
            }
        } catch (NoSuchFieldException e) {
            throw new BaseException(STORE_SORT_FIELD_NOT_FOUND);
        }
    }

    public boolean isOpenNow(Store store) {
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

    public boolean isClosedToday(Store store) {
        Calendar cal = Calendar.getInstance();
        int todayDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        log.info(String.valueOf(todayDayOfWeek)); // 오늘 요일
        String dayOff = store.getDayOff();
        List<Day> dayOffList = EnumUtils.getEnumListFromString(dayOff, Day.class);
        for (Day day : dayOffList) {
            // 휴무일일 경우
            if (todayDayOfWeek == day.getDayOfWeek()) {
                return true;
            }
        }
        return false;
    }

    public Page<Store> findAllByNameAndState(String storeName, Pageable pageable) {
        try {
            for (Sort.Order order : pageable.getSort()) {
                String sortField = order.getProperty();
                Store.class.getDeclaredField(sortField);
            }

            List<Store> nameList =
                    storeRepository.findAllByStoreNameAndStateOrderByIdAsc(storeName, ACTIVE);
            List<Store> containNameList =
                    storeRepository.findAllByStoreNameContainingIgnoreCaseAndStateOrderByIdAsc(
                            storeName, ACTIVE);
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
    public void updateTemporarilyClosed(AdminStoreTemporaryClosedRequest request, Long storeId) {
        storeRepository
                .findByIdAndState(storeId, ACTIVE)
                .orElseThrow(() -> new BaseException(STORE_NOT_FOUND))
                .updateTemporarilyClosed(request.isTemporarilyClosed());
    }
}

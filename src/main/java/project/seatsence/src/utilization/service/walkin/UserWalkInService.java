package project.seatsence.src.utilization.service.walkin;

import static project.seatsence.global.code.ResponseCode.INVALID_UTILIZATION_TIME;
import static project.seatsence.global.constants.Constants.UTILIZATION_TIME_UNIT;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.ACTIVE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.store.domain.CustomUtilizationField;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreCustomService;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;
import project.seatsence.src.utilization.dao.CustomUtilizationContentRepository;
import project.seatsence.src.utilization.dao.walkin.WalkInRepository;
import project.seatsence.src.utilization.domain.CustomUtilizationContent;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.request.ChairUtilizationRequest;
import project.seatsence.src.utilization.dto.request.CustomUtilizationContentRequest;
import project.seatsence.src.utilization.dto.request.SpaceUtilizationRequest;
import project.seatsence.src.utilization.dto.response.AllUtilizationsForSeatAndDateResponse;
import project.seatsence.src.utilization.dto.response.walkin.UserWalkInListResponse;
import project.seatsence.src.utilization.service.UserUtilizationService;
import project.seatsence.src.utilization.service.UtilizationService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWalkInService {
    private final UserUtilizationService userUtilizationService;
    private final WalkInService walkInService;
    private final UserService userService;
    private final StoreChairService storeChairService;
    private final StoreSpaceService storeSpaceService;
    private final StoreService storeService;
    private final StoreCustomService storeCustomService;
    private final CustomUtilizationContentRepository customUtilizationContentRepository;

    private final WalkInRepository walkInRepository;
    private final UtilizationService utilizationService;

    /**
     * 가능한 바로사용 시간 단위 유효성 체크
     *
     * @param endDateTime
     * @return 바로사용 시간 단위 조건 충족 여부
     */
    public Boolean isPossibleWalkInTimeUnit(LocalDateTime endDateTime) {
        boolean result = false;

        if (endDateTime.getMinute() == 00 || endDateTime.getMinute() == UTILIZATION_TIME_UNIT) {
            result = true;
        }
        return result;
    }

    /**
     * 바로사용 시작 일자가 현재 일자인지
     *
     * @param startDateTime
     * @return 가능한 바로사용 일자 조건 충족 여부
     */
    public Boolean isPossibleWalkInStartSchedule(LocalDateTime startDateTime) {
        boolean result = false;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lowerLimitConsideringDelay =
                now.minusMinutes(1); // 요청 딜레이 고려해, 요청 가능한 시간 하한선 설정

        if ((startDateTime.isAfter(lowerLimitConsideringDelay)
                        || startDateTime.isEqual(lowerLimitConsideringDelay))
                && startDateTime.isBefore(now)) {
            result = true;
        }
        return result;
    }

    public void inputChairWalkIn(String userEmail, ChairUtilizationRequest chairUtilizationRequest)
            throws JsonProcessingException {

        inputChairAndSpaceWalkInBusinessValidation(
                chairUtilizationRequest.getStartSchedule(),
                chairUtilizationRequest.getEndSchedule());

        StoreChair storeChairFound =
                storeChairService.findByIdAndState(chairUtilizationRequest.getStoreChairId());

        userUtilizationService.inputChairUtilization(
                chairUtilizationRequest.getStartSchedule(),
                chairUtilizationRequest.getEndSchedule(),
                storeChairFound);

        Store storeFound =
                storeService.findByIdAndState(storeChairFound.getStoreSpace().getStore().getId());

        User userFound = userService.findByEmailAndState(userEmail);

        WalkIn walkIn =
                createAndSaveWalkIn(
                        storeFound,
                        null,
                        storeChairFound,
                        userFound,
                        chairUtilizationRequest.getStartSchedule(),
                        chairUtilizationRequest.getEndSchedule());

        for (CustomUtilizationContentRequest request :
                chairUtilizationRequest.getCustomUtilizationContents()) {

            CustomUtilizationField customUtilizationField =
                    storeCustomService.findByIdAndState(request.getFieldId());

            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(request.getContent());

            CustomUtilizationContent newCustomUtilizationContent =
                    new CustomUtilizationContent(
                            userFound, customUtilizationField, null, walkIn, content);
            customUtilizationContentRepository.save(newCustomUtilizationContent);
        }
    }

    public void inputSpaceWalkIn(String userEmail, SpaceUtilizationRequest spaceUtilizationRequest)
            throws JsonProcessingException {

        inputChairAndSpaceWalkInBusinessValidation(
                spaceUtilizationRequest.getStartSchedule(),
                spaceUtilizationRequest.getEndSchedule());

        StoreSpace storeSpaceFound =
                storeSpaceService.findByIdAndState(spaceUtilizationRequest.getStoreSpaceId());

        userUtilizationService.inputSpaceUtilization(
                spaceUtilizationRequest.getStartSchedule(),
                spaceUtilizationRequest.getEndSchedule(),
                storeSpaceFound);

        Store storeFound = storeService.findByIdAndState(storeSpaceFound.getStore().getId());

        User userFound = userService.findByEmailAndState(userEmail);

        WalkIn walkIn =
                createAndSaveWalkIn(
                        storeFound,
                        storeSpaceFound,
                        null,
                        userFound,
                        spaceUtilizationRequest.getStartSchedule(),
                        spaceUtilizationRequest.getEndSchedule());

        for (CustomUtilizationContentRequest request :
                spaceUtilizationRequest.getCustomUtilizationContents()) {

            CustomUtilizationField customUtilizationField =
                    storeCustomService.findByIdAndState(request.getFieldId());

            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(request.getContent());

            CustomUtilizationContent newCustomUtilizationContent =
                    new CustomUtilizationContent(
                            userFound, customUtilizationField, null, walkIn, content);
            customUtilizationContentRepository.save(newCustomUtilizationContent);
        }
    }

    /* '의자'와 '스페이스' 바로사용에 공통적으로 적용되는 비지니스 유효성 검사 */
    void inputChairAndSpaceWalkInBusinessValidation(
            LocalDateTime startSchedule, LocalDateTime endSchedule) {
        if (!isPossibleWalkInTimeUnit(endSchedule)) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        if (!isPossibleWalkInStartSchedule(startSchedule)) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }
    }

    WalkIn createAndSaveWalkIn(
            Store store,
            StoreSpace storeSpace,
            StoreChair storeChair,
            User user,
            LocalDateTime startSchedule,
            LocalDateTime endSchedule) {
        WalkIn walkIn =
                WalkIn.builder()
                        .store(store)
                        .usedStoreSpace(storeSpace)
                        .usedStoreChair(storeChair)
                        .user(user)
                        .startSchedule(startSchedule)
                        .endSchedule(endSchedule)
                        .build();

        walkInService.save(walkIn);
        return walkIn;
    }

    public Slice<WalkIn> getAllWalkIn(String userEmail, Pageable pageable) {
        User user = userService.findUserByUserEmailAndState(userEmail);

        Slice<WalkIn> walkInSlice =
                findAllByUserEmailAndStateOrderByStartScheduleDesc(userEmail, pageable);

        return walkInSlice;
    }

    Slice<WalkIn> findAllByUserEmailAndStateOrderByStartScheduleDesc(
            String email, Pageable pageable) {
        return walkInRepository.findAllByUserEmailAndStateOrderByStartScheduleDesc(
                email, ACTIVE, pageable);
    }

    public SliceResponse<UserWalkInListResponse.WalkInResponse> toSliceResponse(
            Slice<WalkIn> walkInSlice) {
        return SliceResponse.of(walkInSlice.map(this::toWalkInResponse));
    }

    private UserWalkInListResponse.WalkInResponse toWalkInResponse(WalkIn walkIn) {
        String walkInUnitWalkedInByUser = null;
        String walkedInPlace = null;
        String storeSpaceName = null;

        if (walkIn.getUsedStoreSpace() != null) {
            walkInUnitWalkedInByUser = "스페이스";
            walkedInPlace = walkIn.getUsedStoreSpace().getName();
            storeSpaceName = walkIn.getUsedStoreSpace().getName();
        } else {
            walkInUnitWalkedInByUser = "좌석";
            walkedInPlace = String.valueOf(walkIn.getUsedStoreChair().getManageId());
            storeSpaceName = walkIn.getUsedStoreChair().getStoreSpace().getName();
        }
        return UserWalkInListResponse.WalkInResponse.builder()
                .id(walkIn.getId())
                .storeName(walkIn.getStore().getStoreName())
                .storeSpaceName(storeSpaceName)
                .walkInUnitWalkedInByUser(walkInUnitWalkedInByUser)
                .walkedInPlace(walkedInPlace)
                .startSchedule(walkIn.getStartSchedule())
                .endSchedule(walkIn.getEndSchedule())
                .createdAt(walkIn.getCreatedAt())
                .storeMainImage(storeService.getStoreMainImage(walkIn.getStore().getId()))
                .userNickname(walkIn.getUser().getNickname())
                .build();
    }

    public List<AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate> getAllWalkInsForSpaceAndDate(Long spaceId, LocalDateTime standardTime) {
        List<WalkIn> walkInList = new ArrayList<>();

        StoreSpace storeSpace = storeSpaceService.findByIdAndState(spaceId);

        LocalDateTime limit = utilizationService.setLimitTimeToGetAllReservationsOfThatDay(standardTime);

        WalkIn walkInFoundBySpace = walkInService.findByUsedStoreSpaceAndEndScheduleIsAfterAndState(storeSpace, standardTime);

        Utilization utilizationFoundBySpaceWalkIn = utilizationService.findByWalkInAndState(walkInFoundBySpace);
        if(utilizationFoundBySpaceWalkIn != null) {
            if(utilizationFoundBySpaceWalkIn.getUtilizationStatus() == UtilizationStatus.CHECK_IN) {
                walkInList.add(walkInFoundBySpace);

                List<AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate> mappedWalkIns =
                        walkInList.stream()
                                .map(
                                        walkIn ->
                                                AllUtilizationsForSeatAndDateResponse
                                                        .UtilizationForSeatAndDate.from(walkIn))
                                .collect(Collectors.toList());

                return mappedWalkIns;
            }
        }

        List<StoreChair> storeChairList = storeChairService.findAllByStoreSpaceAndState(storeSpace);

        for (StoreChair storeChair : storeChairList) {
            List<WalkIn> walkInsByChairInSpace =
                    findAllByReservedStoreChairAndReservationStatusInAndEndScheduleIsAfterAndEndScheduleIsBeforeAndState(
                            storeChair, reservationStatuses, standardTime, limit);

            for (Reservation reservation : reservationsByChairInSpace) {
                reservationList.add(reservation);
            }
        }

        Collections.sort(reservationList, startScheduleComparator);

        List<AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate> mappedWalkIns =
                walkInList.stream()
                        .map(
                                walkIn ->
                                        AllUtilizationsForSeatAndDateResponse
                                                .UtilizationForSeatAndDate.from(walkIn))
                        .collect(Collectors.toList());

        return mappedWalkIns;
    }
}

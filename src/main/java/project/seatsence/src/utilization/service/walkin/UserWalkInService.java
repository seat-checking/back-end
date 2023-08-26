package project.seatsence.src.utilization.service.walkin;

import static project.seatsence.global.code.ResponseCode.INVALID_UTILIZATION_TIME;
import static project.seatsence.global.constants.Constants.UTILIZATION_TIME_UNIT;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;
import project.seatsence.src.utilization.domain.walkin.WalkIn;
import project.seatsence.src.utilization.dto.ChairUtilizationRequest;
import project.seatsence.src.utilization.dto.SpaceUtilizationRequest;
import project.seatsence.src.utilization.service.UserUtilizationService;

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

    public void inputChairWalkIn(
            ChairUtilizationRequest chairUtilizationRequest,
            User user,
            StoreChair storeChair,
            Store store) {

        if (!isPossibleWalkInTimeUnit(chairUtilizationRequest.getEndSchedule())) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        if (!isPossibleWalkInStartSchedule(chairUtilizationRequest.getStartSchedule())) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        userUtilizationService.inputChairUtilization(
                chairUtilizationRequest.getStartSchedule(),
                chairUtilizationRequest.getEndSchedule(),
                storeChair);

        WalkIn walkIn =
                WalkIn.builder()
                        .store(store)
                        .storeSpace(null)
                        .storeChair(storeChair)
                        .user(user)
                        .startSchedule(chairUtilizationRequest.getStartSchedule())
                        .endSchedule(chairUtilizationRequest.getEndSchedule())
                        .build();

        walkInService.save(walkIn);
    }

    public void inputSpaceWalkIn(
            String userEmail, SpaceUtilizationRequest spaceUtilizationRequest) {

        if (!isPossibleWalkInTimeUnit(spaceUtilizationRequest.getEndSchedule())) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        if (!isPossibleWalkInStartSchedule(spaceUtilizationRequest.getStartSchedule())) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        StoreSpace storeSpaceFound =
                storeSpaceService.findByIdAndState(spaceUtilizationRequest.getStoreSpaceId());

        userUtilizationService.inputSpaceUtilization(
                spaceUtilizationRequest.getStartSchedule(),
                spaceUtilizationRequest.getEndSchedule(),
                storeSpaceFound);

        User userFound = userService.findByEmailAndState(userEmail);

        Store storeFound = storeService.findByIdAndState(storeSpaceFound.getStore().getId());

        WalkIn walkIn =
                WalkIn.builder()
                        .store(storeFound)
                        .storeSpace(storeSpaceFound)
                        .storeChair(null)
                        .user(userFound)
                        .startSchedule(spaceUtilizationRequest.getStartSchedule())
                        .endSchedule(spaceUtilizationRequest.getEndSchedule())
                        .build();

        walkInService.save(walkIn);
    }
}

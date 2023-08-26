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

    public void inputChairWalkIn(
            ChairUtilizationRequest chairUtilizationRequest,
            User user,
            StoreChair storeChair,
            Store store) {

        if (!isPossibleWalkInTimeUnit(chairUtilizationRequest.getEndSchedule())) {
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

    public void inputSpaceWalkIn(String userEmail, SpaceUtilizationRequest spaceUtilizationRequest) {
        User userFound = userService.findByEmailAndState(userEmail);

        StoreSpace storeSpaceFound =
                storeSpaceService.findByIdAndState(spaceUtilizationRequest.getStoreSpaceId());

        Store storeFound =
                storeService.findByIdAndState(storeSpaceFound.getStore().getId());



    }
}

package project.seatsence.src.utilization.service;

import static project.seatsence.global.code.ResponseCode.INVALID_RESERVATION_UNIT;
import static project.seatsence.global.code.ResponseCode.INVALID_UTILIZATION_TIME;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.utilization.dto.response.AllUtilizationsForSeatAndDateResponse;
import project.seatsence.src.utilization.service.reservation.UserReservationService;
import project.seatsence.src.utilization.service.walkin.UserWalkInService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserUtilizationService {
    private final StoreChairService storeChairService;
    private final StoreSpaceService storeSpaceService;
    private final UserReservationService userReservationService;
    private final UserWalkInService userWalkInService;

    /**
     * 이용 시작일과 종료일이 같은날인지 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 이용 시작일과 종료일이 같은날인지 여부
     */
    public Boolean isStartDateIsEqualEndDate(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = false;

        if ((startDateTime.toLocalDate().isEqual(endDateTime.toLocalDate()))
                || (isUntilMidnightOnTheSameDay(startDateTime, endDateTime))) {
            result = true;
        }
        return result;
    }

    /**
     * 이용 끝 시간이 이용 시작일의 자정인지 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 이용 끝 시간이 이용 시작일의 자정인지 여부
     */
    public Boolean isUntilMidnightOnTheSameDay(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean result = false;
        if ((endDateTime.getHour() == 00) && (endDateTime.getMinute() == 00)) {
            if (startDateTime.toLocalDate().plusDays(1).isEqual(endDateTime.toLocalDate())) {
                result = true;
            }
        }

        return result;
    }

    /**
     * 최소 이용 시간 유효성 체크 (30분)
     *
     * @param startDateTime
     * @param endDateTime
     * @return 최소 이용 시간 조건 충족 여부
     */
    public Boolean isMoreThanMinimumUtilizationTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = true;

        if (startDateTime.getHour() == endDateTime.getHour()) {
            if (!isUntilMidnightOnTheSameDay(startDateTime, endDateTime)) {
                if (endDateTime.isBefore(startDateTime.plusMinutes(30))) {
                    result = false;
                }
            }
        }
        return result;
    }

    /**
     * 이용 시작 스케쥴이 종료 스케쥴 이전인지 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 이용 시작 스케쥴이 종료 스케쥴 이전인지 여부
     */
    public Boolean isStartScheduleIsBeforeEndSchedule(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = false;

        if (startDateTime.isBefore(endDateTime)) {
            result = true;
        }
        return result;
    }

    /* 바로사용과 예약에서 공통으로 사용하는 의자 이용 관련 Service*/
    public void inputChairUtilization(
            LocalDateTime startSchedule, LocalDateTime endSchedule, StoreChair storeChair) {
        if (storeSpaceService.reservationUnitIsOnlySpace(storeChair.getStoreSpace())) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }
        inputChairAndSpaceUtilization(startSchedule, endSchedule);
    }

    /* 바로사용과 예약에서 공통으로 사용하는 스페이스 이용 관련 Service*/
    public void inputSpaceUtilization(
            LocalDateTime startSchedule, LocalDateTime endSchedule, StoreSpace storeSpaceFound) {
        if (storeSpaceService.reservationUnitIsOnlySeat(storeSpaceFound)) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }
        inputChairAndSpaceUtilization(startSchedule, endSchedule);
    }

    /* 바로사용과 예약에서 공통으로 사용하는 스페이스 및 의자 이용 관련 Service*/
    void inputChairAndSpaceUtilization(LocalDateTime startSchedule, LocalDateTime endSchedule) {
        if (!isMoreThanMinimumUtilizationTime(startSchedule, endSchedule)) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        if (!isStartDateIsEqualEndDate(startSchedule, endSchedule)) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }

        if (!isStartScheduleIsBeforeEndSchedule(startSchedule, endSchedule)) {
            throw new BaseException(INVALID_UTILIZATION_TIME);
        }
    }

    public void getAllUtilizationsForSpaceAndDate(Long spaceId, LocalDateTime standardTime) {
        LocalDateTime now = LocalDateTime.now();
        List<AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate> list;

        // 예약
        List<AllUtilizationsForSeatAndDateResponse.UtilizationForSeatAndDate> mappedReservations =
                userReservationService.getAllReservationsForSpaceAndDate(spaceId, standardTime);

        list = mappedReservations;

        if(now.isEqual(standardTime)) {
            // 바로사용
            userWalkInService.getAllWalkInsForSpaceAndDate(spaceId, standardTime);

        }



    }


}

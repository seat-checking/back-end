package project.seatsence.src.utilization.service.reservation;

import static project.seatsence.global.constants.Constants.*;
import static project.seatsence.global.entity.BaseTimeAndStateEntity.State.*;
import static project.seatsence.src.utilization.domain.reservation.ReservationStatus.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.global.response.SliceResponse;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;
import project.seatsence.src.utilization.dao.reservation.ReservationRepository;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.reservation.ReservationStatus;
import project.seatsence.src.utilization.dto.reservation.response.UserReservationListResponse;

@Service
@Transactional
@RequiredArgsConstructor
public class UserReservationService {
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ReservationService reservationService;

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    /**
     * 가능한 예약 시간 단위 유효성 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 예약 시간 단위 조건 충족 여부
     */
    public Boolean isPossibleReservationTimeUnit(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = true;

        if (startDateTime.getMinute() != 0
                && startDateTime.getMinute() != RESERVATION_OR_USE_TIME_UNIT) {
            result = false;
        }
        return result;
    }

    /**
     * 최소 예약 시간 유효성 체크 (30분)
     *
     * @param startDateTime
     * @param endDateTime
     * @return 최소 예약 시간 조건 충족 여부
     */
    public Boolean isMoreThanMinimumReservationTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = true;

        if (startDateTime.getHour() == endDateTime.getHour()) {
            if ((!isUntilMidnightOnTheSameDay(startDateTime, endDateTime))
                    && (startDateTime.getMinute() == endDateTime.getMinute())) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 예약 LocalDateTime이 현재 LocalDateTime 이후인지
     *
     * @param startDateTime
     * @return 가능한 예약 일자 조건 충족 여부
     */
    public Boolean reservationDateTimeIsAfterOrEqualNowDateTime(LocalDateTime startDateTime) {
        boolean result = false;
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDateTime) || now.isEqual(startDateTime)) {
            result = true;
        }
        return result;
    }

    /**
     * 예약 시작일과 종료일이 같은날인지 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 예약 시작일과 종료일이 같은날인지 여부
     */
    public Boolean startDateIsEqualEndDate(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = false;

        if ((startDateTime.toLocalDate().isEqual(endDateTime.toLocalDate()))
                || (isUntilMidnightOnTheSameDay(startDateTime, endDateTime))) {
            result = true;
        }
        return result;
    }

    public Boolean startDateTimeIsBeforeEndDateTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = false;

        if (startDateTime.isBefore(endDateTime)) {
            result = true;
        }
        return result;
    }

    /**
     * 당일 예약인지 아닌지
     *
     * @param startDateTime
     * @return 당일 예약 여부
     */
    public Boolean isSameDayReservation(LocalDateTime startDateTime) {
        boolean result = false;
        LocalDate now = LocalDate.now();

        if (now.isEqual(startDateTime.toLocalDate())) {
            result = true;
        }
        return result;
    }

    /**
     * 예약 끝 시간이 예약 시작일의 자정인지 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 예약 끝 시간이 예약 시작일의 자정인지 여부
     */
    public Boolean isUntilMidnightOnTheSameDay(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Boolean result = false;
        if (endDateTime.getHour() == 0) {
            if (endDateTime.getMinute() == 0) {
                if (startDateTime.toLocalDate().plusDays(1).isEqual(endDateTime.toLocalDate())) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 당일 예약 시작 시간 유효성 체크 (현시간 기준 3시간 이후부터 가능)
     *
     * @param startDateTime
     * @return 당일 예약 시작 시간 조건 충족 여부
     */
    // Todo : 클라에서 요청 들어온 시간에서 서비스단까지 오다가 0.xx초 차이로 단위가 넘어가버려서 유효하지않아지면 어떻게하지?
    public Boolean isPossibleSameDayReservationStartDateAndTime(LocalDateTime startDateTime) {
        boolean result = true;
        LocalDateTime now = LocalDateTime.now();

        // 시, 분 체크
        if (now.getMinute() == 0) { // xx시 '00분'
            if (!(startDateTime.getHour() >= now.getHour() + MIN_HOURS_FOR_SAME_DAY_RESERVATION)) {
                result = false;
            }
        }

        if (now.getMinute() == 30) { // xx시 '30분'
            if (startDateTime.getHour() == now.getHour() + MIN_HOURS_FOR_SAME_DAY_RESERVATION) {
                if (startDateTime.getMinute() != 30) {
                    result = false;
                }
            }
        }

        if (now.getMinute() > RESERVATION_OR_USE_TIME_UNIT) { // xx시 '30분' 초과
            if (!(startDateTime.getHour()
                    >= now.getHour() + (MIN_HOURS_FOR_SAME_DAY_RESERVATION + 1))) {
                result = false;
            }
        }

        if (now.getMinute() < RESERVATION_OR_USE_TIME_UNIT) { // xx시 30분 미만
            if (startDateTime.getHour() == now.getHour() + MIN_HOURS_FOR_SAME_DAY_RESERVATION) {
                if (startDateTime.getMinute() != RESERVATION_OR_USE_TIME_UNIT) {
                    result = false;
                }
            } else if (!(startDateTime.getHour()
                    > now.getHour() + MIN_HOURS_FOR_SAME_DAY_RESERVATION)) {
                result = false;
            }
        }
        return result;
    }

    public SliceResponse<UserReservationListResponse> getUserReservationList(
            Long userId, String reservationStatus, Pageable pageable) {
        User user = userService.findById(userId);

        return SliceResponse.of(
                reservationRepository
                        .findAllByUserAndReservationStatusAndStateOrderByReservationStartDateAndTimeDesc(
                                user,
                                ReservationStatus.valueOfKr(reservationStatus),
                                ACTIVE,
                                pageable)
                        .map(UserReservationListResponse::from));
    }

    public void cancelReservation(Reservation reservation) {
        reservationService.checkValidationToModifyReservationStatus(reservation);

        reservation.setReservationStatus(CANCELED);
    }
}

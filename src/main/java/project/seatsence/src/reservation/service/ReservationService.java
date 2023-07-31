package project.seatsence.src.reservation.service;

import static project.seatsence.global.constants.Constants.MIN_HOURS_FOR_SAME_DAY_RESERVATION;
import static project.seatsence.global.constants.Constants.RESERVATION_OR_USE_TIME_UNIT;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seatsence.src.reservation.dao.ReservationRepository;
import project.seatsence.src.reservation.domain.Reservation;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public void saveReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    /**
     * 가능한 예약 단위 유효성 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 예약 시간으로 사용 가능한지 (true = 가능)
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
     * 예약 시작 시간 유효성 체크 (현시간 기준 3시간 이후부터 가능)
     *
     * @param inputDateTime
     * @return 예약 시작 시간으로 사용 가능한지 (true = 가능)
     */
    // Todo : 클라에서 요청 들어온 시간에서 서비스단까지 오다가 0.xx초 차이로 단위가 넘어가버려서 유효하지않아지면 어떻게하지?
    public Boolean isPossibleReservationStartDateAndTime(LocalDateTime inputDateTime) {
        boolean result = true;
        LocalDateTime now = LocalDateTime.now();

        // 년, 월, 일 체크
        if ((now.getYear() != inputDateTime.getYear())
                || now.getMonth() != inputDateTime.getMonth()
                || now.getDayOfMonth() != inputDateTime.getDayOfMonth()) {
            result = false;
        }

        // 시, 분 체크
        if (now.getMinute() == 0) {
            if (!(inputDateTime.getHour()
                    >= now.getHour()
                            + MIN_HOURS_FOR_SAME_DAY_RESERVATION)) {
                result = false;
            }
        }

        if (now.getMinute() >= RESERVATION_OR_USE_TIME_UNIT) { // 30분 이상
            if (!(inputDateTime.getHour()
                    >= now.getHour()
                            + (MIN_HOURS_FOR_SAME_DAY_RESERVATION
                                    + 1))) {
                result = false;
            }
        }

        if (now.getMinute() < RESERVATION_OR_USE_TIME_UNIT) { // 30분 미만
            if (inputDateTime.getHour()
                    == now.getHour()
                            + MIN_HOURS_FOR_SAME_DAY_RESERVATION) {
                if (inputDateTime.getMinute() != RESERVATION_OR_USE_TIME_UNIT) {
                    result = false;
                }
            }
            if (!(inputDateTime.getHour()
                    > now.getHour()
                            + MIN_HOURS_FOR_SAME_DAY_RESERVATION)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 예약 끝 시간 유효성 체크
     *
     * @param startDateTime
     * @param endDateTime
     * @return 예약 끝 시간으로 사용 가능한지 (true = 가능)
     */
    public Boolean isPossibleReservationEndDateAndTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        boolean result = true;
        LocalDateTime now = LocalDateTime.now();

        // 년, 월, 일 체크
        if ((now.getYear() != endDateTime.getYear())
                || now.getMonth() != endDateTime.getMonth()
                || now.getDayOfMonth() != endDateTime.getDayOfMonth()) {
            result = false;
        }

        // 시, 분 체크
        if (startDateTime.getHour() > endDateTime.getHour()) {
            result = false;
        }

        if (startDateTime.getHour() == endDateTime.getHour()) {
            if (!(startDateTime.getMinute() < endDateTime.getMinute())) {
                result = false;
            }
        }
        return result;
    }
}

package project.seatsence.src.reservation.service;

import static project.seatsence.global.constants.Constants.CAN_HOUR_OF_SAME_DAY_RESERVATION_START_TIME_FROM_THE_CURRENT_TIME;
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
     * 예약 시작 시간 유효성 체크 (현시간 기준 3시간 이후부터 가능)
     *
     * @param inputDateTime
     * @return 예약 시작 시간으로 사용 가능한지 (true = 가능)
     */
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
                            + CAN_HOUR_OF_SAME_DAY_RESERVATION_START_TIME_FROM_THE_CURRENT_TIME)) {
                result = false;
            }
        }

        if (now.getMinute() >= RESERVATION_OR_USE_TIME_UNIT) { // 30분 이상
            if (!(inputDateTime.getHour()
                    >= now.getHour()
                            + (CAN_HOUR_OF_SAME_DAY_RESERVATION_START_TIME_FROM_THE_CURRENT_TIME
                                    + 1))) {
                result = false;
            }
        }

        if (now.getMinute() < RESERVATION_OR_USE_TIME_UNIT) { // 30분 미만
            if (inputDateTime.getHour()
                    == now.getHour()
                            + CAN_HOUR_OF_SAME_DAY_RESERVATION_START_TIME_FROM_THE_CURRENT_TIME) {
                if (inputDateTime.getMinute() != RESERVATION_OR_USE_TIME_UNIT) {
                    result = false;
                }
            }
            if (!(inputDateTime.getHour()
                    > now.getHour()
                            + CAN_HOUR_OF_SAME_DAY_RESERVATION_START_TIME_FROM_THE_CURRENT_TIME)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 예약 끝 시간 유효성 체크
     *
     * @param startDateTime, endDateTime
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

package project.seatsence.src.reservation.api;

import static project.seatsence.global.code.ResponseCode.*;
import static project.seatsence.src.reservation.domain.ReservationStatus.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.seatsence.global.exceptions.BaseException;
import project.seatsence.src.reservation.domain.Reservation;
import project.seatsence.src.reservation.dto.request.SeatReservationRequest;
import project.seatsence.src.reservation.dto.request.SpaceReservationRequest;
import project.seatsence.src.reservation.dto.response.UserReservationListResponse;
import project.seatsence.src.reservation.service.UserReservationService;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.service.StoreChairService;
import project.seatsence.src.store.service.StoreSpaceService;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.user.service.UserService;

@RestController
@RequestMapping("/v1/reservations/users")
@Tag(name = "05. [reservation]")
@Validated
@RequiredArgsConstructor
public class UserReservationApi {
    private final UserReservationService userReservationService;
    private final StoreChairService storeChairService;
    private final StoreSpaceService storeSpaceService;
    private final UserService userService;

    @Operation(summary = "유저 좌석 예약")
    @PostMapping("/seat")
    public void seatReservation(@RequestBody SeatReservationRequest seatReservationRequest) {
        StoreChair storeChairFound =
                storeChairService.findByIdAndState(seatReservationRequest.getStoreChairId());

        if (storeSpaceService.reservationUnitIsOnlySpace(storeChairFound.getStoreSpace())) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }

        if (!userReservationService.isPossibleReservationTimeUnit(
                seatReservationRequest.getReservationStartDateAndTime(),
                seatReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.isMoreThanMinimumReservationTime(
                seatReservationRequest.getReservationStartDateAndTime(),
                seatReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.reservationDateTimeIsAfterOrEqualNowDateTime(
                seatReservationRequest.getReservationStartDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.startDateIsEqualEndDate(
                seatReservationRequest.getReservationStartDateAndTime(),
                seatReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.startDateTimeIsBeforeEndDateTime(
                seatReservationRequest.getReservationStartDateAndTime(),
                seatReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        // 당일예약 유효성 체크
        if (userReservationService.isSameDayReservation(
                seatReservationRequest.getReservationStartDateAndTime())) {
            if (!userReservationService.isPossibleSameDayReservationStartDateAndTime(
                    seatReservationRequest.getReservationStartDateAndTime())) {
                throw new BaseException(INVALID_RESERVATION_TIME);
            }
        }

        User userFound = userService.findById(seatReservationRequest.getUserId());

        Reservation reservation =
                Reservation.builder()
                        .storeChair(storeChairFound)
                        .storeSpace(null)
                        .user(userFound)
                        .reservationStartDateAndTime(
                                seatReservationRequest.getReservationStartDateAndTime())
                        .reservationEndDateAndTime(
                                seatReservationRequest.getReservationEndDateAndTime())
                        .reservationStatus(PENDING)
                        .build();

        userReservationService.saveReservation(reservation);
    }

    @Operation(summary = "유저 스페이스 예약")
    @PostMapping("/space")
    public void spaceReservation(@RequestBody SpaceReservationRequest spaceReservationRequest) {
        StoreSpace storeSpaceFound =
                storeSpaceService.findByIdAndState(spaceReservationRequest.getStoreSpaceId());

        if (storeSpaceService.reservationUnitIsOnlySeat(storeSpaceFound)) {
            throw new BaseException(INVALID_RESERVATION_UNIT);
        }

        if (!userReservationService.isPossibleReservationTimeUnit(
                spaceReservationRequest.getReservationStartDateAndTime(),
                spaceReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.isMoreThanMinimumReservationTime(
                spaceReservationRequest.getReservationStartDateAndTime(),
                spaceReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.reservationDateTimeIsAfterOrEqualNowDateTime(
                spaceReservationRequest.getReservationStartDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.startDateIsEqualEndDate(
                spaceReservationRequest.getReservationStartDateAndTime(),
                spaceReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        if (!userReservationService.startDateTimeIsBeforeEndDateTime(
                spaceReservationRequest.getReservationStartDateAndTime(),
                spaceReservationRequest.getReservationEndDateAndTime())) {
            throw new BaseException(INVALID_RESERVATION_TIME);
        }

        // 당일예약 유효성 체크
        if (userReservationService.isSameDayReservation(
                spaceReservationRequest.getReservationStartDateAndTime())) {
            if (!userReservationService.isPossibleSameDayReservationStartDateAndTime(
                    spaceReservationRequest.getReservationStartDateAndTime())) {
                throw new BaseException(INVALID_RESERVATION_TIME);
            }
        }

        User userFound = userService.findById(spaceReservationRequest.getUserId());

        Reservation reservation =
                Reservation.builder()
                        .storeChair(null)
                        .storeSpace(storeSpaceFound)
                        .user(userFound)
                        .reservationStartDateAndTime(
                                spaceReservationRequest.getReservationStartDateAndTime())
                        .reservationEndDateAndTime(
                                spaceReservationRequest.getReservationEndDateAndTime())
                        .reservationStatus(PENDING)
                        .build();

        userReservationService.saveReservation(reservation);
    }

    @Operation(summary = "유저 예약 현황 조회")
    @GetMapping("/my-list/{user-id}")
    public List<UserReservationListResponse> getUserReservationList(
            @PathVariable("user-id") Long userId, @RequestParam String reservationStatus) {
        return userReservationService.getUserReservationList(userId, reservationStatus);
    }
}

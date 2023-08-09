package project.seatsence.src.reservation.dto.response;

import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.src.reservation.domain.Reservation;

@Getter
@Builder
public class UserReservationListResponse {
    @Parameter(name = "예약 식별자", description = "예약 id", example = "1")
    private Long reservationId;

    @Parameter(name = "가게명", description = "예약한 가게 이름", example = "Catch cafe")
    private String storeName;

    @Parameter(
            name = "유저가 예약한 예약단위",
            description = "유저가 좌석을 예약했는지, 스페이스를 예약했는지에 대한 정보",
            example = "좌석")
    private String reservationUnitReservedByUser;

    @Parameter(name = "스페이스명", description = "예약한 가게의 스페이스명", example = "Catch cafe")
    private String storeSpaceName;

    @Parameter(
            name = "의자 관리 id",
            description = "예약한 가게의 의자 관리id (가게의 의자 식별자(id) 아님)",
            example = "chair-1")
    private String storeChairManageId;

    @Parameter(name = "예약 시작 일정", description = "예약 시작 날짜와 시간", example = "2023-08-07T10:30:00.000")
    private LocalDateTime reservationStartDateAndTime;

    @Parameter(name = "예약 끝 일정", description = "예약 끝 날짜와 시간", example = "2023-08-07T11:30:00.000")
    private LocalDateTime reservationEndDateAndTime;

    public static UserReservationListResponse from(Reservation reservation) {
        String reservationUnitReservedByUser = null;
        String storeSpaceName = null;
        String storeChairManageId = null;

        if (reservation.getStoreChair() == null) {
            reservationUnitReservedByUser = "스페이스";
            storeSpaceName = reservation.getStoreSpace().getName();
        } else if (reservation.getStoreSpace() == null) {
            reservationUnitReservedByUser = "좌석";
            storeChairManageId = reservation.getStoreChair().getManageId();
        }

        return UserReservationListResponse.builder()
                .reservationId(reservation.getId())
                .storeName(reservation.getStore().getStoreName())
                .reservationUnitReservedByUser(reservationUnitReservedByUser)
                .storeSpaceName(storeSpaceName)
                .storeChairManageId(storeChairManageId)
                .reservationStartDateAndTime(reservation.getReservationStartDateAndTime())
                .reservationEndDateAndTime(reservation.getReservationEndDateAndTime())
                .build();
    }
}

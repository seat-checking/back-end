package project.seatsence.src.utilization.domain.reservation;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.utilization.domain.HoldingStatus;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Reservation extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "reserved_store_space_id")
    private StoreSpace reservedStoreSpace; // 스페이스 예약일 때만, 해당 스페이스

    @Nullable
    @ManyToOne
    @JoinColumn(name = "reserved_store_chair_id")
    private StoreChair reservedStoreChair; // 의자 예약일 때만, 해당 의자

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull private LocalDateTime startSchedule;
    @NotNull private LocalDateTime endSchedule;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    private ReservationStatus reservationStatus;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'BEFORE'")
    private HoldingStatus holdingStatus;

    @Builder
    public Reservation(
            Store store,
            StoreSpace reservedStoreSpace,
            StoreChair reservedStoreChair,
            User user,
            LocalDateTime startSchedule,
            LocalDateTime endSchedule) {
        this.store = store;
        this.reservedStoreSpace = reservedStoreSpace;
        this.reservedStoreChair = reservedStoreChair;
        this.user = user;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
    }

    public void cancelReservation() {
        reservationStatus = ReservationStatus.CANCELED;
    }

    public void approveReservation() {
        reservationStatus = ReservationStatus.APPROVED;
    }

    public void rejectReservation() {
        reservationStatus = ReservationStatus.REJECTED;
    }

    public void startHolding() {
        holdingStatus = HoldingStatus.IN_PROCESSING;
    }

    public void endHolding() {
        holdingStatus = HoldingStatus.PROCESSED;
    }
}

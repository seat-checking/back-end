package project.seatsence.src.utilization.domain.reservation;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.user.domain.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private StoreSpace reservedStoreSpace;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "reserved_store_chair_id")
    private StoreChair reservedStoreChair;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull private LocalDateTime startSchedule;
    @NotNull private LocalDateTime endSchedule;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @Builder
    public Reservation(
            Store store,
            StoreSpace storeSpace,
            StoreChair storeChair,
            User user,
            LocalDateTime startSchedule,
            LocalDateTime endSchedule,
            ReservationStatus reservationStatus) {
        this.store = store;
        this.reservedStoreSpace = storeSpace;
        this.reservedStoreChair = storeChair;
        this.user = user;
        this.startSchedule = startSchedule;
        this.endSchedule = endSchedule;
        this.reservationStatus = reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}

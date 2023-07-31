package project.seatsence.src.reservation.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;
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

    @Nullable
    @ManyToOne
    @JoinColumn(name = "store_chair_id")
    private StoreChair storeChair;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "store_space_id")
    private StoreSpace storeSpace;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull private LocalDateTime reservationStartDateAndTime;
    @NotNull private LocalDateTime reservationEndDateAndTime;

    @NotNull private ReservationStatus reservationStatus;

    @Builder
    public Reservation(
            StoreChair storeChair,
            StoreSpace storeSpace,
            User user,
            LocalDateTime reservationStartDateAndTime,
            LocalDateTime reservationEndDateAndTime) {
        this.storeChair = storeChair;
        this.storeSpace = storeSpace;
        this.user = user;
        this.reservationStartDateAndTime = reservationStartDateAndTime;
        this.reservationEndDateAndTime = reservationEndDateAndTime;
    }
}

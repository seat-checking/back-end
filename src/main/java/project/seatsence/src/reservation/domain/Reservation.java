package project.seatsence.src.reservation.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.user.domain.User;

@Getter
public class Reservation extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "id")
    private StoreChair storeChair;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "id")
    private StoreSpace storeSpace;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @NotNull private LocalDateTime reservationStartDateAndTime;
    @NotNull private LocalDateTime reservationEndDateAndTime;

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

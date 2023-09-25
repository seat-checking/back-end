package project.seatsence.src.holding.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holding extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Nullable
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Nullable
    @OneToOne
    @JoinColumn(name = "walk_in_id")
    private WalkIn walkIn;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @NotNull private LocalDateTime startTime;

    @NotNull
    @ColumnDefault("'IN_PROGRESSING")
    private HoldingStatus holdingStatus;
}

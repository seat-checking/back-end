package project.seatsence.src.utilization.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

/** Utilization(이용) = Reservation(예약) + Use(뱌로사용) */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Utilization extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Nullable
    @OneToOne
    @JoinColumn(name = "walk_in_id")
    private WalkIn walkIn;

    @Nullable
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'CHECK_IN'")
    private UtilizationStatus utilizationStatus;

    @CreationTimestamp
    @Column(updatable = false)
    @NotNull
    private LocalDateTime startSchedule; // 이용 (예약 or 바로사용) 시작시간

    @Nullable private LocalDateTime endSchedule; // 이용 끝시간
}

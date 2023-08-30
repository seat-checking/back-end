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
import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

/** Utilization(이용) = Reservation(예약) + Walk-In(뱌로사용) */
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_space_id")
    private StoreSpace storeSpace; // 이용 단위 상관없이 무조건 해당 스페이스가 맵핑되어 있어야 함

    @Nullable
    @OneToOne
    @JoinColumn(name = "walk_in_id")
    private WalkIn walkIn;

    @Nullable
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'HOLDING'")
    private UtilizationStatus utilizationStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationUnit utilizationUnit;

    @CreationTimestamp
    @Column(updatable = false)
    @NotNull
    private LocalDateTime startSchedule; // 이용 (예약 or 바로사용) 시작시간

    @NotNull private LocalDateTime endSchedule; // 이용 끝시간

    public void forceCheckOut() {
        this.utilizationStatus = UtilizationStatus.FORCED_CHECK_OUT;
        this.endSchedule = LocalDateTime.now();
    }
}

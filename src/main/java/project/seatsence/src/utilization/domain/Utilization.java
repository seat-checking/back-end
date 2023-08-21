package project.seatsence.src.utilization.domain;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.use.Use;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Utilization extends BaseEntity {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @OneToOne
    @JoinColumn(name = "use_id")
    private Use use;

    @Nullable
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @NotNull private LocalDateTime startSchedule;
    @NotNull private LocalDateTime endSchedule;
}

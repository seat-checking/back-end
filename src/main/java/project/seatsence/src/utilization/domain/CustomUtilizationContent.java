package project.seatsence.src.utilization.domain;

import javax.annotation.Nullable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.CustomUtilizationField;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.utilization.domain.reservation.Reservation;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUtilizationContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // field
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_utilization_field_id")
    private CustomUtilizationField field;

    // reservation
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // WalkIn
    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walk_in_id")
    private WalkIn walkIn;

    @Column(nullable = false)
    private String content;

    @Builder
    public CustomUtilizationContent(User user, CustomUtilizationField field, Reservation reservation, WalkIn walkIn, String content) {
        this.user = user;
        this.field = field;
        this.reservation = reservation;
        this.walkIn = walkIn;
        this.content = content;
    }
}

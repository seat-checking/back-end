package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.src.user.domain.User;
import project.seatsence.src.utilization.domain.reservation.Reservation;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomReservationContent {
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
    @JoinColumn(name = "custom_reservation_field_id")
    private CustomReservationField customReservationField;

    // reservation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column(nullable = false)
    private String content;
}

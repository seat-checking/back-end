package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "store_space")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreSpace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotBlank private String name;

    @NotNull private int height;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "예약 단위를 선택해주세요.")
    private ReservationUnit reservationUnit;
}

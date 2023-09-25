package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Table(name = "store_chair")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreChair extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "의자의 웹 관리용 id를 입력해주세요.")
    private String idByWeb; // 프론트엔드에서 관리용으로 사용하는 id

    @PositiveOrZero(message = "의자의 관리 ID를 입력해주세요.")
    private int manageId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(targetEntity = StoreSpace.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_space_id")
    private StoreSpace storeSpace;

    @PositiveOrZero(message = "알맞은 chair의 x 좌표를 입력해주세요.")
    private int chairX;

    @PositiveOrZero(message = "알맞은 chair의 y 좌표를 입력해주세요.")
    private int chairY;

    @ColumnDefault("false")
    private boolean isInUse;
}

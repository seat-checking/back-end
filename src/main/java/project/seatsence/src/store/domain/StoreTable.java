package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_table")
public class StoreTable extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = StoreSpace.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_space_id")
    private StoreSpace storeSpace;

    @NotBlank(message = "테이블의 관리 ID를 입력해주세요.")
    private String manageId;

    @PositiveOrZero(message = "알맞은 table의 x 좌표를 입력해주세요.")
    private int tableX;

    @PositiveOrZero(message = "알맞은 table의 y 좌표를 입력해주세요.")
    private int tableY;

    @PositiveOrZero(message = "알맞은 table의 가로 길이를 입력해주세요.")
    private int width;

    @PositiveOrZero(message = "알맞은 table의 세로 길이를 입력해주세요.")
    private int height;

    @ColumnDefault("false")
    private boolean isInUse;
}

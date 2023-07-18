package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "store_chair")
public class StoreChair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "의자의 관리 ID를 입력해주세요.")
    private String manageId;

    @ManyToOne(targetEntity = StoreTable.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_table_id")
    private StoreTable storeTable;

    @PositiveOrZero(message = "알맞은 chair의 x 좌표를 입력해주세요.")
    private int chairX;

    @PositiveOrZero(message = "알맞은 chair의 y 좌표를 입력해주세요.")
    private int chairY;

    @ColumnDefault("false")
    private boolean isInUse;
}

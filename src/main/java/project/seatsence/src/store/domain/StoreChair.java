package project.seatsence.src.store.domain;

import javax.persistence.*;
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

    @ManyToOne(targetEntity = StoreTable.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_table_id")
    private StoreTable storeTable;

    @PositiveOrZero private int chairX;

    @PositiveOrZero private int chairY;

    @ColumnDefault("false")
    private boolean isInUse;
}

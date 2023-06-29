package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Table(name = "store_table")
public class StoreTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = StoreSpace.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_space_id")
    private StoreSpace storeSpace;

    @PositiveOrZero private int tableX;

    @PositiveOrZero private int tableY;

    @ColumnDefault("false")
    private boolean isInUse;
}

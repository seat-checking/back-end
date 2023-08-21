package project.seatsence.src.utilization.domain.use;

import java.time.LocalDateTime;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.entity.BaseEntity;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.user.domain.User;

@Getter
@Entity
@NoArgsConstructor
public class Use extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "reserved_store_space_id")
    private StoreSpace reservedStoreSpace;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "reserved_store_chair_id")
    private StoreChair reservedStoreChair;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull private LocalDateTime startSchedule;
    @NotNull private LocalDateTime endSchedule;
}

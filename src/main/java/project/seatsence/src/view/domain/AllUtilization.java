package project.seatsence.src.view.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.src.store.domain.Category;

/**
 * 예약 + 바로사용
 */
@NoArgsConstructor
@Getter
@Entity
public class AllUtilization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String utilizationKind;

    @Column(nullable = false)
    private LocalDateTime startSchedule;

    @Column(nullable = true)
    private LocalDateTime endSchedule;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private Category storeCategory;
}

package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "store_space")
public class StoreSpace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @NotBlank private String name;

    @NotNull private int width;

    @NotNull private int height;

    @Column(name = "entrance_x")
    @Positive
    private int entranceX;

    @Column(name = "entrance_y")
    @Positive
    private int entranceY;
}

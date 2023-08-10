package project.seatsence.src.store.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "store_wifi")
public class StoreWifi extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = TempStore.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private TempStore tempStore;

    @NotBlank private String wifi;
}

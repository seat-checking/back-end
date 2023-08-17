package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomReservationField extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String title;

    @Enumerated(EnumType.STRING)
    private CustomReservationFieldType type;

    private String contentGuide;

    @Builder
    public CustomReservationField(
            Store store, String title, CustomReservationFieldType type, String contentGuide) {
        this.store = store;
        this.title = title;
        this.type = type;
        this.contentGuide = contentGuide;
    }
}

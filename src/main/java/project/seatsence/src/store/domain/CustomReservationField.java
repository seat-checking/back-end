package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomReservationField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String title;

    @Enumerated(EnumType.STRING)
    private CustomReservationType type;

    private String contentGuide;

    @Builder
    public CustomReservationField(
            Store store,
            String title,
            CustomReservationType type,
            String contentGuide){
        this.store =store;
        this.title=title;
        this.type=type;
        this.contentGuide=contentGuide;
    }
}

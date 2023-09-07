package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.Getter;
import project.seatsence.global.entity.BaseEntity;

@Entity
@Getter
public class StoreImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String url;

    public static StoreImage createStoreImage(Store store, String url) {
        StoreImage storeImage = new StoreImage();
        storeImage.store = store;
        storeImage.url = url;
        return storeImage;
    }
}

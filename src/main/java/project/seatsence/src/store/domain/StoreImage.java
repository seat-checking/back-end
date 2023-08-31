package project.seatsence.src.store.domain;

import javax.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class StoreImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Store.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public String url;

    public static StoreImage createStoreImage(Store store, String url) {
        StoreImage storeImage = new StoreImage();
        storeImage.store = store;
        storeImage.url = url;
        return storeImage;
    }
}

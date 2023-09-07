package project.seatsence.src.store.dao;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.*;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreImage;

@Repository
public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {

    List<StoreImage> findAllByStoreAndState(Store store, State state);
}

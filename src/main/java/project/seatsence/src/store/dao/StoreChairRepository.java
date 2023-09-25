package project.seatsence.src.store.dao;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreSpace;

@Repository
public interface StoreChairRepository extends JpaRepository<StoreChair, Long> {

    Optional<StoreChair> findByIdAndState(Long id, State state);

    List<StoreChair> findAllByStoreSpaceAndState(StoreSpace storeSpace, State state);

    List<StoreChair> findAllByStoreAndState(Store store, State state);
}

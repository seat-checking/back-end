package project.seatsence.src.store.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.State;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.store.domain.StoreTable;

@Repository
public interface StoreTableRepository extends JpaRepository<StoreTable, Long> {

    List<StoreTable> findAllByStoreSpaceAndState(StoreSpace storeSpace, State state);
}

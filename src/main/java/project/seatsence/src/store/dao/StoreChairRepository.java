package project.seatsence.src.store.dao;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreTable;

@Repository
public interface StoreChairRepository extends JpaRepository<StoreChair, Long> {

    List<StoreChair> findAllByStoreTable(StoreTable storeTable);

    Optional<StoreChair> findByIdAndState(Long id, State state);
}

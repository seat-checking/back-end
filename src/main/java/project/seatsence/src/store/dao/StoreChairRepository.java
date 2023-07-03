package project.seatsence.src.store.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.StoreChair;
import project.seatsence.src.store.domain.StoreTable;

@Repository
public interface StoreChairRepository extends JpaRepository<StoreChair, Long> {

    List<StoreChair> findAllByStoreTable(StoreTable storeTable);
}

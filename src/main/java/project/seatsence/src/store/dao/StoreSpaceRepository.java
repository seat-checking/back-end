package project.seatsence.src.store.dao;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.State;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreSpace;

@Repository
public interface StoreSpaceRepository extends JpaRepository<StoreSpace, Long> {

    List<StoreSpace> findAllByStoreAndState(Store store, State state);

    Optional<StoreSpace> findByIdAndState(Long id, State state);

    @Query(
            "SELECT s FROM StoreSpace s JOIN FETCH s.storeTableList WHERE s.id = :id AND s.state = :state")
    Optional<StoreSpace> findByIdAndStateWithTables(
            @Param("id") Long id, @Param("state") State state);
}

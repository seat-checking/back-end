package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Store;
import project.seatsence.src.store.domain.StoreSpace;

@Repository
public interface StoreSpaceRepository extends JpaRepository<StoreSpace, Long> {

    List<StoreSpace> findAllByStore(Store store);

    Optional<StoreSpace> findById(Long id);
}

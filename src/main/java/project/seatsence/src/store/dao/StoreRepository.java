package project.seatsence.src.store.dao;

import static project.seatsence.global.entity.BaseTimeAndStateEntity.*;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByIdAndState(Long id, State state);

    List<Store> findAllByIdInAndState(List<Long> idList, State state);

    Page<Store> findAllByState(State state, Pageable pageable);

    Page<Store> findAllByCategoryAndState(Category category, State state, Pageable pageable);

    List<Store> findAllByStoreNameAndStateOrderByIdAsc(String StoreName, State state);

    List<Store> findAllByStoreNameContainingIgnoreCaseAndStateOrderByIdAsc(
            String StoreName, State state);

}

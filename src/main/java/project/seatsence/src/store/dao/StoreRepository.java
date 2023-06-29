package project.seatsence.src.store.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;

import javax.validation.constraints.NotNull;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);

    Page<Store> findAllByState(BaseTimeAndStateEntity.State state, Pageable pageable);

    Page<Store> findAllByStateAndCategory(
            BaseTimeAndStateEntity.State state, @NotNull Category category, Pageable pageable);


}

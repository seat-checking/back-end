package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.Category;
import project.seatsence.src.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);

    Page<Store> findAllByState(BaseTimeAndStateEntity.State state, Pageable pageable);

    Page<Store> findAllByStateAndCategory(
            BaseTimeAndStateEntity.State state, @NotNull Category category, Pageable pageable);

    List<Store> findALlByStateAndNameOrderByIdAsc(
            BaseTimeAndStateEntity.State state, @NotBlank String name);

    List<Store> findAllByStateAndNameContainingIgnoreCaseOrderByIdAsc(
            BaseTimeAndStateEntity.State state, @NotBlank String name);
}

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
import project.seatsence.src.store.domain.TempStore;

@Repository
public interface TempStoreRepository extends JpaRepository<TempStore, Long> {

    Optional<TempStore> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);

    Page<TempStore> findAllByState(BaseTimeAndStateEntity.State state, Pageable pageable);

    Page<TempStore> findAllByStateAndCategory(
            BaseTimeAndStateEntity.State state, @NotNull Category category, Pageable pageable);

    List<TempStore> findALlByStateAndNameOrderByIdAsc(
            BaseTimeAndStateEntity.State state, @NotBlank String name);

    List<TempStore> findAllByStateAndNameContainingIgnoreCaseOrderByIdAsc(
            BaseTimeAndStateEntity.State state, @NotBlank String name);

    List<TempStore> findAllByAdminInfoIdIn(List<Long> adminInfoIds);
}

package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.CustomUtilizationField;

public interface CustomUtilizationFieldRepository extends JpaRepository<CustomUtilizationField, Long> {
    List<CustomUtilizationField> findAllByStoreIdAndState(
            Long storeId, BaseTimeAndStateEntity.State state);

    Optional<CustomUtilizationField> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);
}

package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.CustomReservationField;

public interface StoreCustomRepository extends JpaRepository<CustomReservationField, Long> {
    List<CustomReservationField> findAllByStoreIdAndState(
            Long storeId, BaseTimeAndStateEntity.State state);

    Optional<CustomReservationField> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);
}

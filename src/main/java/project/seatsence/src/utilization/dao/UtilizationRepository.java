package project.seatsence.src.utilization.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.store.domain.ReservationUnit;
import project.seatsence.src.store.domain.StoreSpace;
import project.seatsence.src.utilization.domain.Utilization;
import project.seatsence.src.utilization.domain.UtilizationStatus;

public interface UtilizationRepository extends JpaRepository<Utilization, Long> {
    Optional<Utilization> findByIdAndState(Long id, State state);

    List<Utilization>
            findByStoreSpaceAndUtilizationUnitAndUtilizationStatusOrUtilizationStatusAndState(
                    StoreSpace storeSpace,
                    ReservationUnit utilizationUnit,
                    UtilizationStatus utilizationStatus1,
                    UtilizationStatus utilizationStatus2,
                    State state);
}

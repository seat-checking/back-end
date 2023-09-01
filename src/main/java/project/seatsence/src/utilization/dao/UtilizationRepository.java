package project.seatsence.src.utilization.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity.*;
import project.seatsence.src.utilization.domain.Utilization;

public interface UtilizationRepository
        extends JpaRepository<Utilization, Long>, UtilizationRepositoryCustom {
    Optional<Utilization> findByIdAndState(Long id, State state);
}

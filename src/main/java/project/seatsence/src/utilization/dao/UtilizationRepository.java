package project.seatsence.src.utilization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.utilization.domain.Utilization;

import java.util.Optional;

public interface UtilizationRepository extends JpaRepository<Utilization,Long> {
    Optional<Utilization> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);
}

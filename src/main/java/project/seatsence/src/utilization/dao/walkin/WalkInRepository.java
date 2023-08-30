package project.seatsence.src.utilization.dao.walkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

import java.util.Optional;

@Repository
public interface WalkInRepository extends JpaRepository<WalkIn, Long> {
    WalkIn save(WalkIn walkIn);

    Optional<WalkIn> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);
}

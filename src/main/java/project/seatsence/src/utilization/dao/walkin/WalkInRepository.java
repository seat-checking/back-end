package project.seatsence.src.utilization.dao.walkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.utilization.domain.walkin.WalkIn;

@Repository
public interface WalkInRepository extends JpaRepository<WalkIn, Long> {
    WalkIn save(WalkIn walkIn);
}

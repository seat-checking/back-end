package project.seatsence.src.utilization.dao.use;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.utilization.domain.use.Use;

@Repository
public interface UseRepository extends JpaRepository<Use, Long> {
    Use save(Use use);
}

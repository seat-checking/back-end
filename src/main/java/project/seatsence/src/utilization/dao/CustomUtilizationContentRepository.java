package project.seatsence.src.utilization.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.utilization.domain.CustomUtilizationContent;

public interface CustomUtilizationContentRepository extends JpaRepository<CustomUtilizationContent,Long> {
}

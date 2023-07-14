package project.seatsence.src.admin.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.admin.domain.AdminInfo;

public interface AdminInfoRepository extends JpaRepository<AdminInfo, Long> {

    List<AdminInfo> findAllByUserId(Long userId);
}

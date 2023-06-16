package project.seatsence.src.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.admin.domain.AdminInfo;

public interface AdminInfoRepository extends JpaRepository<AdminInfo, Long> {}

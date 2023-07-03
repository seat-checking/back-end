package project.seatsence.src.admin.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.admin.domain.AdminMemberAuthority;

public interface AdminMemberAuthorityRepository extends JpaRepository<AdminMemberAuthority, Long> {
    Boolean existsByUserId(Long userId);

    Optional<AdminMemberAuthority> findByUserId(Long userId);
}

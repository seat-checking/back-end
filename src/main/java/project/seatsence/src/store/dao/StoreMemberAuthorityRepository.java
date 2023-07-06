package project.seatsence.src.store.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.store.domain.StoreMemberAuthority;

public interface StoreMemberAuthorityRepository extends JpaRepository<StoreMemberAuthority, Long> {
    Boolean existsByUserId(Long userId);

    Optional<StoreMemberAuthority> findByUserId(Long userId);
}

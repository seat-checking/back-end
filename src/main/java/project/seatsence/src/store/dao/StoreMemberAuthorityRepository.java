package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.StoreMemberAuthority;

@Repository
public interface StoreMemberAuthorityRepository extends JpaRepository<StoreMemberAuthority, Long> {
    Boolean existsByUserIdAndState(Long userId, BaseTimeAndStateEntity.State state);

    List<StoreMemberAuthority> findAllByStoreIdAndState(
            Long storeId, BaseTimeAndStateEntity.State state);

    Optional<StoreMemberAuthority> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);

    Optional<StoreMemberAuthority> findByUserId(Long userId);
}

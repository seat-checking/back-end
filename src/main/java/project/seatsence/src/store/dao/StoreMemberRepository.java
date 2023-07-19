package project.seatsence.src.store.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.global.entity.BaseTimeAndStateEntity;
import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.domain.StorePosition;

@Repository
public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {
    Boolean existsByUserIdAndState(Long userId, BaseTimeAndStateEntity.State state);

    Optional<StoreMember> findByIdAndState(Long id, BaseTimeAndStateEntity.State state);

    List<StoreMember> findAllByStoreIdAndPositionAndState(
            Long id, StorePosition position, BaseTimeAndStateEntity.State state);

    Optional<StoreMember> findByUserId(Long userId);
}

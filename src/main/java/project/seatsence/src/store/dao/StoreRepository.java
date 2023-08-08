package project.seatsence.src.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.seatsence.src.store.domain.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}

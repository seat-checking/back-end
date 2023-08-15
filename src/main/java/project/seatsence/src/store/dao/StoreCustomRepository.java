package project.seatsence.src.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.store.domain.CustomReservationField;

public interface StoreCustomRepository extends JpaRepository<CustomReservationField, Long> {}

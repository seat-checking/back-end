package project.seatsence.src.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seatsence.src.user.dto.CustomUserDetailsDto;

public interface CustomUserDetailsRepository extends JpaRepository<CustomUserDetailsDto, Long> {
    CustomUserDetailsDto findByEmail(String email);
}

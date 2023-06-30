package project.seatsence.src.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class AdminSignInResponse {

    @Id
    private Long id;
    
}

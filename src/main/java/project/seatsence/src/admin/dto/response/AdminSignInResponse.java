package project.seatsence.src.admin.dto.response;

import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSignInResponse {

    @Id private Long id;
}

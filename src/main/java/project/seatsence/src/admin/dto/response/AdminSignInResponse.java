package project.seatsence.src.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.seatsence.src.store.domain.StorePosition;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignInResponse {
    private String accessToken;
}

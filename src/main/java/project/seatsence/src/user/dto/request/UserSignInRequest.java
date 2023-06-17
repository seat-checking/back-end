package project.seatsence.src.user.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UserSignInRequest {
    private String email;

    private String password;
}

package project.seatsence.src.user.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class FindUserByEmailRequest {
    private String email;
}

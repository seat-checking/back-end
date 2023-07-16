package project.seatsence.src.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindUserByEmailResponse {
    private String email;
    private String nickname;
}

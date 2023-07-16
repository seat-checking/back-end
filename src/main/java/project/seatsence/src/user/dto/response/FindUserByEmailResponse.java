package project.seatsence.src.user.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindUserByEmailResponse {
    private String email;
    private String nickname;
}

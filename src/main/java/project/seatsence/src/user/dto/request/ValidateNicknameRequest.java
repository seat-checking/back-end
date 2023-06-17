package project.seatsence.src.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.annotation.ValidNickname;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateNicknameRequest {

    @ValidNickname
    private String nickname;
}

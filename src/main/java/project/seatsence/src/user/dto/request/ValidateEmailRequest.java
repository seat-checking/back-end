package project.seatsence.src.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateEmailRequest {
    //to do : email 유효성
    private String email;
}

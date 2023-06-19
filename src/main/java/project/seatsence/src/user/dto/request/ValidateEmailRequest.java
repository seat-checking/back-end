package project.seatsence.src.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.seatsence.global.annotation.ValidEmail;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateEmailRequest {
    @ValidEmail private String email;
}

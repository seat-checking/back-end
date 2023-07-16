package project.seatsence.src.user.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class FindUserByEmailRequest {
    @NotBlank(message = "이메일이 입력되지 않았습니다.")
    private String email;
}

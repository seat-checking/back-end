package project.seatsence.global.exceptions;

import lombok.*;
import project.seatsence.global.code.ResponseCode;

@Getter
@Builder
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private ResponseCode responseCode;

}

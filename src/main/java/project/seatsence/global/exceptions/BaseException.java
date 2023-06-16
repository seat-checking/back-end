package project.seatsence.global.exceptions;

import lombok.*;
import project.seatsence.global.code.ResponseCode;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private ResponseCode responseCode;

    public BaseException(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
}

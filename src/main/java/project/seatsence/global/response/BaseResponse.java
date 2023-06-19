package project.seatsence.global.response;

import static project.seatsence.global.code.ResponseCode.SUCCESS;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "status", "code", "message", "result"})
public class BaseResponse {
    private final Boolean isSuccess = true;
    private final int status;
    private final String code;
    private final String message;
    private Object result;

    // 요청 성공
    public BaseResponse(int status, Object result) {
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.status = status;
        this.result = result;
    }
}

package project.seatsence.global.response;

import static project.seatsence.global.code.ResponseCode.SUCCESS;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonPropertyOrder({"isSuccess", "status", "code", "message", "data"})
public class BaseResponse {
    private final Boolean isSuccess = true;
    private final int status;
    private final String code;
    private final String message;
    private Object data;

    // 요청 성공
    public BaseResponse(int status, Object data) {
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.status = status;
        this.data = data;
    }
}

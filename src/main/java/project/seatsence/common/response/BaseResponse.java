package project.seatsence.common.response;

import static project.seatsence.common.code.ResponseCode.SUCCESS;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonPropertyOrder({"isSuccess", "status", "code", "message", "data", "timestamp"})
public class BaseResponse {
    private final Boolean isSuccess = true;
    private final int status;
    private final String code;
    private final String message;
    private Object data;
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

    // 요청 성공
    public BaseResponse(int status, Object data) {
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.status = status;
        this.data = data;
    }
}

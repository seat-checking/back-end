package project.seatsence.src.utilization.api.use;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.seatsence.src.utilization.dao.use.UseRepository;

@RestController
@RequestMapping("/v1/uses/users")
@Tag(name = "07. [Use - User]", description = "유저에 관한 바로 사용 API")
@Validated
@RequiredArgsConstructor
public class UserUseApi {

    private UseRepository useRepository;
}

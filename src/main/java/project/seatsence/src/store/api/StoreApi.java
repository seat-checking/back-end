package project.seatsence.src.store.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/stores")
@RestController
@RequiredArgsConstructor
@Tag(name = "05. [Store]")
@Slf4j
@Validated
public class StoreApi {}

package project.seatsence.src.store.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    CAFE("카페"),
    RESTAURANT("음식점"),
    SPACE("모임"),
    ETC("기타");

    private final String value;
}

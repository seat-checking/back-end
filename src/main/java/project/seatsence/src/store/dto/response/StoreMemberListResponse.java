package project.seatsence.src.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StoreMemberListResponse {

    private List<StoreMemberResponse> storeMemberResponseList;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class StoreMemberResponse {
        private Long id;
        private String name;
        private String introduction;
        private String location;
        private String mainImage;
    }
}

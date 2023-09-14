package project.seatsence.src.utilization.dto.response.walkin;

import java.time.LocalDateTime;
import java.util.List;

import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserWalkInListResponse {
    private List<UserWalkInListResponse.WalkInResponse> walkInResponseList;

    @Getter
    @AllArgsConstructor
    @Builder
    public static class WalkInResponse {
        private Long id;
        private String storeName;
        private String storeSpaceName;
        private String walkInUnitWalkedInByUser; // 좌석 또는 스페이스
        private String walkedInPlace; // 유저 바로사용 좌석의 관리 id나 유저가 바로사용하는 스페이스 이름
        private LocalDateTime startSchedule;
        private LocalDateTime endSchedule;
        private LocalDateTime createdAt;
        private String storeMainImage;
        private String userNickname;
    }
}

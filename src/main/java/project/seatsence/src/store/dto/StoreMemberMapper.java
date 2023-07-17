package project.seatsence.src.store.dto;

import project.seatsence.src.store.domain.StoreMemberAuthority;
import project.seatsence.src.store.dto.response.StoreMemberListResponse;

public class StoreMemberMapper {
    public static StoreMemberListResponse.StoreMemberResponse toStoreMemberResponse(
            StoreMemberAuthority storeMember) {
        return StoreMemberListResponse.StoreMemberResponse.builder()
                .id(storeMember.getId())
                .name(storeMember.getUser().getNickname())
                .email(storeMember.getUser().getNickname())
                .permissionByMenu(storeMember.getPermissionByMenu())
                .build();
    }
}

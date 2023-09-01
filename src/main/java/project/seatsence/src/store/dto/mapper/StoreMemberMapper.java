package project.seatsence.src.store.dto.mapper;

import project.seatsence.src.store.domain.StoreMember;
import project.seatsence.src.store.dto.response.admin.member.StoreMemberListResponse;

public class StoreMemberMapper {
    public static StoreMemberListResponse.StoreMemberResponse toStoreMemberResponse(
            StoreMember storeMember) {
        return StoreMemberListResponse.StoreMemberResponse.builder()
                .id(storeMember.getId())
                .name(storeMember.getUser().getName())
                .email(storeMember.getUser().getEmail())
                .permissionByMenu(storeMember.getPermissionByMenu())
                .build();
    }
}

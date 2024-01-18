package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomMemberDto(
        Long memberId,
        String profileImageUrl,
        String name
) {
    public static RoomMemberDto of(Long id, String profileImg, String nickName) {
        return RoomMemberDto.builder()
                .memberId(id)
                .profileImageUrl(profileImg)
                .name(nickName)
                .build();
    }
}

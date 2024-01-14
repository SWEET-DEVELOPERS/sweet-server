package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record RoomMemberDto(
        Long memberId,
        String profileImageUrl,
        String name
) {
    public static RoomMemberDto of(Long memberId, String profileImageUrl, String name) {
        return RoomMemberDto.builder()
                .memberId(memberId)
                .profileImageUrl(profileImageUrl)
                .name(name)
                .build();
    }
}

package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomMemberDetailDto(
        List<RoomMemberDto> members
) {
    public static RoomMemberDetailDto of(List<RoomMemberDto> roomMemberDtoList) {
        return RoomMemberDetailDto.builder()
                .members(roomMemberDtoList)
                .build();
    }
}

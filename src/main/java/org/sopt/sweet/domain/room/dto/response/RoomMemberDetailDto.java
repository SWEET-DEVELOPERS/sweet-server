package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomMemberDetailDto(
        RoomDto room,
        OwnerDto owner,
        List<RoomMemberDto> members
) {
    public static RoomMemberDetailDto of(RoomDto roomDto, OwnerDto ownerDto, List<RoomMemberDto> roomMemberDtoList) {
        return RoomMemberDetailDto.builder()
                .room(roomDto)
                .owner(ownerDto)
                .members(roomMemberDtoList)
                .build();
    }
}

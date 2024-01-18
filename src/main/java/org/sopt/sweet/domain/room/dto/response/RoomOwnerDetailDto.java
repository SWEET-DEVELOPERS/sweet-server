package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record RoomOwnerDetailDto(
        RoomDto room,
        OwnerDto owner
) {
    public static RoomOwnerDetailDto of(RoomDto roomDto, OwnerDto ownerDto) {
        return RoomOwnerDetailDto.builder()
                .room(roomDto)
                .owner(ownerDto)
                .build();
    }
}

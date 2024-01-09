package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record JoinRoomResponseDto(
        Long roomId
) {
    public static JoinRoomResponseDto of(Long roomId) {
        return JoinRoomResponseDto.builder()
                .roomId(roomId)
                .build();
    }
}

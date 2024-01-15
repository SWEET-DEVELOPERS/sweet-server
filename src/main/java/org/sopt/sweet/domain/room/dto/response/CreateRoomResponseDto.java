package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record CreateRoomResponseDto(
        Long roomId,
        String invitationCode
) {
    public static CreateRoomResponseDto of(Long roomId, String invitationCode) {
        return CreateRoomResponseDto.builder()
                .roomId(roomId)
                .invitationCode(invitationCode)
                .build();
    }
}

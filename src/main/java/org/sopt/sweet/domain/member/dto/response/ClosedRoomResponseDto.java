package org.sopt.sweet.domain.member.dto.response;

public record ClosedRoomResponseDto(
        Long roomId,
        String imageUrl,
        String gifteeName,
        int gifterNumber,
        Boolean isOwner
) {
}

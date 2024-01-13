package org.sopt.sweet.domain.member.dto.response;

import java.time.LocalDateTime;

;

public record ActiveRoomResponseDto(
        Long roomId,
        String imageUrl,
        String gifteeName,
        int gifterNumber,
        LocalDateTime tournamentStartDate,
        Boolean isOwner

) {


}
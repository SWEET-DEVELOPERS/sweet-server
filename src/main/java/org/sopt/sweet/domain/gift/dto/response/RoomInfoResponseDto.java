package org.sopt.sweet.domain.gift.dto.response;

import java.time.LocalDateTime;

public record RoomInfoResponseDto(
        String gifteeName,
        LocalDateTime tournamentStartDate
) {
}

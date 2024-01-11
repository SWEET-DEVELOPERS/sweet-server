package org.sopt.sweet.domain.room.dto.request;

import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

public record CreateRoomRequestDto(
        String gifteeName,
        String imageUrl,
        LocalDateTime deliveryDate,
        LocalDateTime tournamentStartDate,
        TournamentDuration tournamentDuration
) {
}

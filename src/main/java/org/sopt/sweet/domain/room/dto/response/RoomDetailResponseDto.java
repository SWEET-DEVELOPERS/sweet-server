package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

@Builder
public record RoomDetailResponseDto(
        Long roomId,
        String imageUrl,
        String gifteeName,
        int gifterNumber,
        LocalDateTime deliveryDate,
        LocalDateTime tournamentStartDate,
        TournamentDuration tournamentDuration
) {
    public static RoomDetailResponseDto of(Long roomId,
                                           String imageUrl,
                                           String gifteeName,
                                           int gifterNumber,
                                           LocalDateTime deliveryDate,
                                           LocalDateTime tournamentStartDate,
                                           TournamentDuration tournamentDuration){
        return RoomDetailResponseDto.builder()
                .roomId(roomId)
                .imageUrl(imageUrl)
                .gifteeName(gifteeName)
                .gifterNumber(gifterNumber)
                .deliveryDate(deliveryDate)
                .tournamentStartDate(tournamentStartDate)
                .tournamentDuration(tournamentDuration)
                .build();
    }

}

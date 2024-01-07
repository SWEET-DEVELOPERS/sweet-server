package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

@Builder
public record RoomInviteResponseDto(
        Long roomId,
        String gifteeName,
        String imageUrl,
        LocalDateTime deliveryDate,
        LocalDateTime tournamentStartDate,
        TournamentDuration tournamentDuration,
        String invitationCode
) {
    public static RoomInviteResponseDto of(Long roomId,
                                           String gifteeName,
                                           String imageUrl,
                                           LocalDateTime deliveryDate,
                                           LocalDateTime tournamentStartDate,
                                           TournamentDuration tournamentDuration,
                                           String invitationCode){
        return RoomInviteResponseDto.builder()
                .roomId(roomId)
                .gifteeName(gifteeName)
                .imageUrl(imageUrl)
                .deliveryDate(deliveryDate)
                .tournamentStartDate(tournamentStartDate)
                .tournamentDuration(tournamentDuration)
                .invitationCode(invitationCode)
                .build();
    }
}

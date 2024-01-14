package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

@Builder
public record TournamentInfoDto(
        LocalDateTime tournamentStartDate,
        TournamentDuration tournamentDuration,

        int TotalParticipantsCount,
        int ParticipantsCount

) {
    public static TournamentInfoDto of(LocalDateTime tournamentStartDate,
                                       TournamentDuration tournamentDuration,
                                       int TotalParticipantsCount,
                                       int ParticipantsCount) {
        return TournamentInfoDto.builder()
                .tournamentStartDate(tournamentStartDate)
                .tournamentDuration(tournamentDuration)
                .TotalParticipantsCount(TotalParticipantsCount)
                .ParticipantsCount(ParticipantsCount)
                .build();
    }

}

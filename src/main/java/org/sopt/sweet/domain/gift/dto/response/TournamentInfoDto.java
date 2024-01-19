package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

@Builder
public record TournamentInfoDto(
        LocalDateTime remainingTime,
        int totalParticipantsCount,
        int participantsCount

) {
    public static TournamentInfoDto of(LocalDateTime remainingTime,
                                       int TotalParticipantsCount,
                                       int ParticipantsCount) {
        return TournamentInfoDto.builder()
                .remainingTime(remainingTime)
                .totalParticipantsCount(TotalParticipantsCount)
                .participantsCount(ParticipantsCount)
                .build();
    }

}

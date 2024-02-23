package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.room.constant.TournamentDuration;

import java.time.LocalDateTime;

@Builder
public record TournamentInfoDto(

        String firstplaceGiftName,
        String firstplaceGiftImageUrl,
        int firstplaceGiftCost,
        LocalDateTime remainingTime,
        int totalParticipantsCount,
        int participantsCount

) {
    public static TournamentInfoDto of( String firstplaceGiftName,
                                        String firstplaceGiftImageUrl,
                                        int firstplaceGiftCost,
                                        LocalDateTime remainingTime,
                                        int totalParticipantsCount,
                                        int participantsCount) {
        return TournamentInfoDto.builder()
                .firstplaceGiftName(firstplaceGiftName)
                .firstplaceGiftImageUrl(firstplaceGiftImageUrl)
                .firstplaceGiftCost(firstplaceGiftCost)
                .remainingTime(remainingTime)
                .totalParticipantsCount(totalParticipantsCount)
                .participantsCount(participantsCount)
                .build();
    }

}

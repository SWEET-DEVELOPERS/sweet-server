package org.sopt.sweet.domain.gift.dto.request;

import lombok.Builder;
import org.sopt.sweet.domain.gift.dto.response.TournamentListsResponseDto;

@Builder
public record TournamentScoreRequestDto(
        Long firstGiftId,
        Long secondGiftId,
        Long finalGiftId
) {
    public static TournamentScoreRequestDto of(Long firstGiftId, Long secondGiftId, Long finalGiftId) {
        return TournamentScoreRequestDto.builder()
                .firstGiftId(firstGiftId)
                .secondGiftId(secondGiftId)
                .finalGiftId(finalGiftId)
                .build();
    }

}

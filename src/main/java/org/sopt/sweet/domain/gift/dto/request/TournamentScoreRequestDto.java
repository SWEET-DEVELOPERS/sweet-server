package org.sopt.sweet.domain.gift.dto.request;

import lombok.Builder;
import org.sopt.sweet.domain.gift.dto.response.TournamentListsResponseDto;

@Builder
public record TournamentScoreRequestDto(
        Long firstPlaceGiftId,
        Long secondPlaceGiftId
) {
    public static TournamentScoreRequestDto of(Long firstPlaceGiftId, Long secondPlaceGiftId) {
        return TournamentScoreRequestDto.builder()
                .firstPlaceGiftId(firstPlaceGiftId)
                .secondPlaceGiftId(secondPlaceGiftId)
                .build();
    }

}

package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TournamentListsResponseDto(
        Long giftId,
        String imageUrl,
        String name,
        int cost
) {
    public static TournamentListsResponseDto of(Long giftId, String imageUrl, String name, int cost) {
        return TournamentListsResponseDto.builder()
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .cost(cost)
                .build();
    }

    public static List<TournamentListsResponseDto> of(List<TournamentListsResponseDto> tournamentListsResponseDtoList) {
        return tournamentListsResponseDtoList;
    }
}



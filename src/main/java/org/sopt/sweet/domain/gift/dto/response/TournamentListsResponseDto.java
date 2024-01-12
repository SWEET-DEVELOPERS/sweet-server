package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TournamentListsResponseDto(
        Long giftId,
        String imageUrl,
        String name,
        int cost,
        String url
) {
    public static TournamentListsResponseDto of(Long giftId, String imageUrl, String name, int cost, String url) {
        return TournamentListsResponseDto.builder()
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .cost(cost)
                .url(url)
                .build();
    }

    public static List<TournamentListsResponseDto> of(List<TournamentListsResponseDto> tournamentListsResponseDtoList) {
        return tournamentListsResponseDtoList;
    }
}



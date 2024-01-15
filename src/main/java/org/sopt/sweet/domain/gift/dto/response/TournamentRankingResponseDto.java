package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;

@Builder
public record TournamentRankingResponseDto(
        Long ranking,
        Long giftId,
        String imageUrl,
        String name,
        int cost,
        String url

) {

    public static TournamentRankingResponseDto of(Long ranking, Long giftId, String imageUrl, String name, int cost, String url) {
        return TournamentRankingResponseDto.builder()
                .ranking(ranking)
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .cost(cost)
                .url(url)
                .build();
    }

}

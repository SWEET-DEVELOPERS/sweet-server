package org.sopt.sweet.domain.gift.dto.request;

public record CreateGiftRequestDto(
        Long roomId,
        String url,
        String name,
        int cost,
        String imageUrl
) {
}

package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;

@Builder
public record MyGiftDto(
        Long giftId,
        String imageUrl,
        String name,
        int cost
) {
    public static MyGiftDto of(Long giftId, String imageUrl, String name, int cost) {
        return MyGiftDto.builder()
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .cost(cost)
                .build();
    }
}

package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record HotProductGiftDto(
        Long productId,
        String imageUrl,
        String name,
        String url,
        int cost
) {
    public static HotProductGiftDto of(Long productId, String imageUrl, String name, String url, int cost) {
        return HotProductGiftDto.builder()
                .productId(productId)
                .imageUrl(imageUrl)
                .name(name)
                .url(url)
                .cost(cost)
                .build();
    }
}

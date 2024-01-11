package org.sopt.sweet.domain.product.dto.response;

import lombok.Builder;

@Builder
public record HotProductDto(
        Long productId,
        String name,
        String imageUrl,
        String url,
        int cost
) {
    public static HotProductDto of(Long productId, String name, String imageUrl, String url, int cost) {
        return HotProductDto.builder()
                .productId(productId)
                .name(name)
                .imageUrl(imageUrl)
                .url(url)
                .cost(cost)
                .build();
    }
}

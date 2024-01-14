package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record RoomMyGiftDto(
        Long giftId,
        String imageUrl,
        String name,
        String url,
        int cost
) {
    public static RoomMyGiftDto of(Long giftId, String imageUrl, String name, String url, int cost) {
        return RoomMyGiftDto.builder()
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .url(url)
                .cost(cost)
                .build();
    }
}

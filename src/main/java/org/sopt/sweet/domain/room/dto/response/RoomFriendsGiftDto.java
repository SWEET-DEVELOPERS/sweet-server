package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record RoomFriendsGiftDto(
        Long giftId,
        String imageUrl,
        String name,
        String url,
        int cost,
        String ownerName
) {
    public static RoomFriendsGiftDto of(Long giftId, String imageUrl, String name, String url, int cost, String ownerName) {
        return RoomFriendsGiftDto.builder()
                .giftId(giftId)
                .imageUrl(imageUrl)
                .name(name)
                .url(url)
                .cost(cost)
                .ownerName(ownerName)
                .build();
    }
}

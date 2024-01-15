package org.sopt.sweet.domain.gift.dto.response;

import lombok.Builder;
import org.sopt.sweet.domain.gift.entity.Gift;

import java.time.LocalDateTime;

@Builder
public record FriendGiftDto(
        Long giftId,
        String imageUrl,
        String name,
        int cost,
        String url,
        String giftOwner
) {

    public static FriendGiftDto of(Gift gift, String giftOwner) {
        return FriendGiftDto.builder()
                .giftId(gift.getId())
                .imageUrl(gift.getImageUrl())
                .name(gift.getName())
                .cost(gift.getCost())
                .url(gift.getUrl())
                .giftOwner(giftOwner)
                .build();
    }

}

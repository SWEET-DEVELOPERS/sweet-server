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
        LocalDateTime createDate
) {

    public static FriendGiftDto of(Gift gift) {
        return new FriendGiftDto(
                gift.getId(),
                gift.getImageUrl(),
                gift.getName(),
                gift.getCost(),
                gift.getUrl(),
                gift.getCreateDate()
        );
    }

}

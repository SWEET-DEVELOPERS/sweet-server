package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record OwnerDto(
      Long ownerId,
      String profileImageUrl,
      String name
) {
    public static OwnerDto of(Long myId, String profileImageUrl, String name) {
        return OwnerDto.builder()
                .ownerId(myId)
                .profileImageUrl(profileImageUrl)
                .name(name)
                .build();
    }
}

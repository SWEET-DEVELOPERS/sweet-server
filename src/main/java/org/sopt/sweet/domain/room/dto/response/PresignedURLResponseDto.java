package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record PresignedURLResponseDto(
        String presignedURL
) {
    public static PresignedURLResponseDto of(String presignedURL){
        return PresignedURLResponseDto.builder()
                .presignedURL(presignedURL)
                .build();
    }
}

package org.sopt.sweet.domain.s3.dto.response;

import lombok.Builder;

@Builder
public record PresignedUrlResponseDto(
        String presignedUrl
) {
    public static PresignedUrlResponseDto of(String presignedUrl){
        return PresignedUrlResponseDto.builder()
                .presignedUrl(presignedUrl)
                .build();
    }
}

package org.sopt.sweet.domain.opengraph.dto;

import lombok.Builder;

@Builder
public record OpengraphResponseDto(
        String title,
        String image
) {
    public static OpengraphResponseDto of(String title,
                                          String image){
        return OpengraphResponseDto.builder()
                .title(title)
                .image(image)
                .build();
    }
}

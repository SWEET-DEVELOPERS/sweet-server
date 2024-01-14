package org.sopt.sweet.domain.product.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record HotProductsResponseDto(
        LocalDateTime tournamentStartDate,
        List<HotProductDto> hotProductDtoList
) {
    public static HotProductsResponseDto of(LocalDateTime tournamentStartDate,
                                            List<HotProductDto> hotProductDtoList) {
        return HotProductsResponseDto.builder()
                .tournamentStartDate(tournamentStartDate)
                .hotProductDtoList(hotProductDtoList)
                .build();
    }
}

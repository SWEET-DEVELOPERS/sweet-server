package org.sopt.sweet.domain.product.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record HotProductsResponseDto(
        String gifteeName,
        LocalDateTime tournamentStartDate,
        List<HotProductDto> hotProductDtoList
) {
    public static HotProductsResponseDto of( String gifteeName,
                                            LocalDateTime tournamentStartDate,
                                            List<HotProductDto> hotProductDtoList) {
        return HotProductsResponseDto.builder()
                .gifteeName(gifteeName)
                .tournamentStartDate(tournamentStartDate)
                .hotProductDtoList(hotProductDtoList)
                .build();
    }
}

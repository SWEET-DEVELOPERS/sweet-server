package org.sopt.sweet.domain.product.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record HotProductsResponseDto(
        List<HotProductDto> hotProductDtoList
) {
    public static HotProductsResponseDto of(List<HotProductDto> hotProductDtoList){
        return HotProductsResponseDto.builder()
                .hotProductDtoList(hotProductDtoList)
                .build();
    }
}

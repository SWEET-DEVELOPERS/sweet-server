package org.sopt.sweet.domain.gift.dto.response;

import java.util.List;

public record MyGiftsResponseDto(
        List<MyGiftDto> myGiftDtoList
) {
}

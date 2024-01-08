package org.sopt.sweet.domain.gift.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gift.dto.request.CreateGiftRequestDto;
import org.sopt.sweet.domain.gift.service.GiftService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gift")
@RestController
public class GiftController implements GiftApi {

    private final GiftService giftService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNewGift(@UserId Long userId, @RequestBody CreateGiftRequestDto createGiftRequestDto) {
        giftService.createNewGift(userId, createGiftRequestDto);
        return SuccessResponse.created(null);
    }
}

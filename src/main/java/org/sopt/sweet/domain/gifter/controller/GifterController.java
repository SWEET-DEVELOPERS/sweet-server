package org.sopt.sweet.domain.gifter.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gifter.dto.request.PresignedURLRequestDto;
import org.sopt.sweet.domain.gifter.dto.response.PresignedURLResponseDto;
import org.sopt.sweet.domain.gifter.service.GifterService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gifter")
@RestController
public class GifterController implements GifterApi {

    private final GifterService gifterService;

    @PostMapping("/presignedURL")
    public ResponseEntity<SuccessResponse<?>> getPresignedURL(@RequestBody PresignedURLRequestDto presignedURLRequestDto) {
        final PresignedURLResponseDto presignedURLResponseDto = gifterService.getPresignedURL(presignedURLRequestDto);
        return SuccessResponse.created(presignedURLResponseDto);
    }
}

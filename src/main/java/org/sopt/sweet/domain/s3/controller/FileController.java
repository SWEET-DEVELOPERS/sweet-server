package org.sopt.sweet.domain.s3.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.s3.dto.request.PresignedUrlRequestDto;
import org.sopt.sweet.domain.s3.dto.response.PresignedUrlResponseDto;
import org.sopt.sweet.domain.s3.service.FileService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/presigned-url")
    public ResponseEntity<SuccessResponse<?>> getPresignedURL(@RequestBody PresignedUrlRequestDto presignedURLRequestDto) {
        final PresignedUrlResponseDto presignedURLResponseDto = fileService.getPresignedURL(presignedURLRequestDto);
        return SuccessResponse.created(presignedURLResponseDto);
    }
}

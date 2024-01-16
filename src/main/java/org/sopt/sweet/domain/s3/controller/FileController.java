package org.sopt.sweet.domain.s3.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.s3.dto.request.PresignedUrlRequestDto;
import org.sopt.sweet.domain.s3.dto.response.PresignedUrlResponseDto;
import org.sopt.sweet.domain.s3.service.FileService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping("/presigned-url")
    public ResponseEntity<SuccessResponse<?>> getPresignedURL(@RequestParam String fileName) {
        final PresignedUrlResponseDto presignedURLResponseDto = fileService.getPresignedURL(fileName);
        return SuccessResponse.created(presignedURLResponseDto);
    }
}

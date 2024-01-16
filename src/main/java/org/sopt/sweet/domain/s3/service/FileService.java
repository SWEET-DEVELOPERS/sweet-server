package org.sopt.sweet.domain.s3.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.s3.dto.request.PresignedUrlRequestDto;
import org.sopt.sweet.domain.s3.dto.response.PresignedUrlResponseDto;
import org.sopt.sweet.global.external.s3.config.FileConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileConfig fileConfig;

    public PresignedUrlResponseDto getPresignedURL(String fileName) {
        String URL = fileConfig.getPreSignedUrl("roomImage", fileName);
        return PresignedUrlResponseDto.of(URL);
    }

}
package org.sopt.sweet.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.dto.request.PresignedURLRequestDto;
import org.sopt.sweet.domain.room.dto.response.PresignedURLResponseDto;
import org.sopt.sweet.global.config.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final FileService fileService;

    public PresignedURLResponseDto getPresignedURL(PresignedURLRequestDto presignedURLRequestDto) {
        String URL = fileService.getPreSignedUrl("gifterImg", presignedURLRequestDto.fileName());
        return PresignedURLResponseDto.of(URL);
    }

}

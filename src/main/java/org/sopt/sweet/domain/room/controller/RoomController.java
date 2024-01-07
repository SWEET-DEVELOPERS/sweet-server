package org.sopt.sweet.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.dto.request.PresignedURLRequestDto;
import org.sopt.sweet.domain.room.dto.response.PresignedURLResponseDto;
import org.sopt.sweet.domain.room.service.RoomService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/room")
@RestController
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @PostMapping("/presigned-url")
    public ResponseEntity<SuccessResponse<?>> getPresignedURL(@RequestBody PresignedURLRequestDto presignedURLRequestDto) {
        final PresignedURLResponseDto presignedURLResponseDto = roomService.getPresignedURL(presignedURLRequestDto);
        return SuccessResponse.created(presignedURLResponseDto);
    }
}

package org.sopt.sweet.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.response.RoomInviteResponseDto;
import org.sopt.sweet.domain.room.service.RoomService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/room")
@RestController
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNewRoom(@UserId Long userId,
                                                            @RequestBody CreateRoomRequestDto createRoomRequestDto){
        final Long roomId = roomService.createNewRoom(userId, createRoomRequestDto);
        return SuccessResponse.created(roomId);
    }

    @GetMapping("/invitations/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getRoomInviteInfo(@PathVariable Long roomId){
        final RoomInviteResponseDto roomInviteResponseDto = roomService.getRoomInviteInfo(roomId);
        return SuccessResponse.ok(roomInviteResponseDto);
    }

}

package org.sopt.sweet.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.response.CreateRoomResponseDto;
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
        final CreateRoomResponseDto newRoom = roomService.createNewRoom(userId, createRoomRequestDto);
        return SuccessResponse.created(newRoom);
    }

    @GetMapping("/invitations/{invitationCode}")
    public ResponseEntity<SuccessResponse<?>> getRoomInviteInfo(@PathVariable String invitationCode){
        final RoomInviteResponseDto roomInviteResponseDto = roomService.getRoomInviteInfo(invitationCode);
        return SuccessResponse.ok(roomInviteResponseDto);
    }
}

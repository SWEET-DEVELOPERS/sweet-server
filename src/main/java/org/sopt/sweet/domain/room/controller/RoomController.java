package org.sopt.sweet.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomImageRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomNameRequestDto;
import org.sopt.sweet.domain.room.dto.response.*;
import org.sopt.sweet.domain.room.service.RoomService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/room")
@RestController
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNewRoom(@UserId Long userId, @RequestBody CreateRoomRequestDto createRoomRequestDto) {
        final CreateRoomResponseDto newRoom = roomService.createNewRoom(userId, createRoomRequestDto);
        return SuccessResponse.created(newRoom);
    }

    @GetMapping("/invitations/{invitationCode}")
    public ResponseEntity<SuccessResponse<?>> getRoomInviteInfo(@PathVariable String invitationCode) {
        final RoomInviteResponseDto roomInviteResponseDto = roomService.getRoomInviteInfo(invitationCode);
        return SuccessResponse.ok(roomInviteResponseDto);
    }

    @PostMapping("/participation")
    public ResponseEntity<SuccessResponse<?>> joinRoom(@UserId Long userId, @RequestParam String invitationCode) {
        final JoinRoomResponseDto joinRoomResponseDto = roomService.findAndJoinRoom(userId, invitationCode);
        return SuccessResponse.ok(joinRoomResponseDto);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getRoomMainInfo(@UserId Long userId, @PathVariable Long roomId) {
        final RoomMainResponseDto roomMainResponseDto = roomService.getRoomMainInfo(userId, roomId);
        return SuccessResponse.ok(roomMainResponseDto);
    }

    @GetMapping("/detail/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getRoomDetailInfo(@UserId Long userId, @PathVariable Long roomId) {
        final RoomDetailResponseDto roomDetailResponseDto = roomService.getRoomDetailInfo(userId, roomId);
        return SuccessResponse.ok(roomDetailResponseDto);
    }

    @PatchMapping("/{roomId}/thumbnail")
    public ResponseEntity<SuccessResponse<?>> modifyRoomThumbnail(@UserId Long userId, @PathVariable Long roomId, @RequestBody RoomImageRequestDto roomImageRequestDto) {
        roomService.modifyRoomThumbnail(userId, roomId, roomImageRequestDto);
        return SuccessResponse.ok(null);
    }

    @PatchMapping("/{roomId}/giftee-name")
    public ResponseEntity<SuccessResponse<?>> modifyRoomGifteeName(@UserId Long userId, @PathVariable Long roomId, @RequestBody RoomNameRequestDto roomNameRequestDto) {
        roomService.modifyRoomGifteeName(userId, roomId, roomNameRequestDto);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/{roomId}/members")
    public ResponseEntity<SuccessResponse<?>> getRoomMembers(@UserId Long userId, @PathVariable Long roomId) {
        final List<RoomMemberDto> roomMemberDtoList = roomService.getRoomMembers(userId, roomId);
        return SuccessResponse.ok(roomMemberDtoList);
    }

    @GetMapping("/{roomId}/room-detail")
    public ResponseEntity<SuccessResponse<?>> getRoom(@UserId Long userId, @PathVariable Long roomId) {
        final RoomOwnerDetailDto roomOwnerDetailDto = roomService.getRoom(userId, roomId);
        return SuccessResponse.ok(roomOwnerDetailDto);
    }

    @DeleteMapping("/{roomId}/members/{memberId}")
    public ResponseEntity<SuccessResponse<?>> deleteRoomMember(@UserId Long userId, @PathVariable Long roomId, @PathVariable Long memberId) {
        roomService.deleteRoomMember(userId, roomId, memberId);
        return SuccessResponse.ok(null);
    }

}

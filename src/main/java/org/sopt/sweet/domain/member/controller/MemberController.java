package org.sopt.sweet.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.*;
import org.sopt.sweet.domain.member.service.MemberService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @GetMapping(path = "/test")
    public ResponseEntity<SuccessResponse<?>> testSwagger() {
        String response = "Sweet little kitty";
        return SuccessResponse.ok(response);
    }

    // 임시 발급 API 입니다. 추후 로그인 기능이 완성되면 삭제할 예정입니다.
    @PostMapping("/token/{memberId}")
    public ResponseEntity<SuccessResponse<?>> getToken(@PathVariable Long memberId) {
        final MemberTokenResponseDto memberTokenResponseDto = memberService.getToken(memberId);
        return SuccessResponse.created(memberTokenResponseDto);
    }

    @GetMapping("/closed-room")
    public ResponseEntity<SuccessResponse<?>> getClosedRoom(@UserId Long userId) {
        final List<ClosedRoomResponseDto> closedRoomResponseDto = memberService.getClosedRoom(userId);
        return SuccessResponse.ok(closedRoomResponseDto);
    }

    @GetMapping("/active-room")
    public ResponseEntity<SuccessResponse<?>> getActiveRoom(@UserId Long userId) {
        final List<ActiveRoomResponseDto> activeRoomResponseDto = memberService.getActiveRoom(userId);
        return SuccessResponse.ok(activeRoomResponseDto);
    }

    @GetMapping("/mypage")
    public ResponseEntity<SuccessResponse<?>> getMyPage(@UserId Long userId) {
        List<ClosedRoomResponseDto> top2ClosedRooms = memberService.getTop2ClosedRooms(userId);
        List<ActiveRoomResponseDto> top2ActiveRooms = memberService.getTop2ActiveRooms(userId);
        MemberInfoDto memberInfo = memberService.getMemberInfo(userId);

        MyPageResponseDto myPageResponseDto = new MyPageResponseDto(
                memberInfo,
                top2ClosedRooms,
                top2ActiveRooms
        );

        return SuccessResponse.ok(myPageResponseDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessResponse<?>> getProfile(@UserId Long userId) {
        ProfileImageResponseDto profile = memberService.getProfile(userId);
        return SuccessResponse.ok(profile);
    }

}

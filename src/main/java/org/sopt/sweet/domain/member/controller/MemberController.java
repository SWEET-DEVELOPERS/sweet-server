package org.sopt.sweet.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.reponse.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.reponse.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.service.MemberService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController implements MemberApi{

    private final MemberService memberService;

    @GetMapping(path = "/test")
    public ResponseEntity<SuccessResponse<?>> testSwagger() {
        String response = "Sweet little kitty";
        return SuccessResponse.ok(response);
    }

    // 임시 발급 API 입니다. 추후 로그인 기능이 완성되면 삭제할 예정입니다.
    @PostMapping("/token/{memberId}")
    public ResponseEntity<SuccessResponse<?>> getToken(@PathVariable Long memberId){
        final MemberTokenResponseDto memberTokenResponseDto = memberService.getToken(memberId);
        return SuccessResponse.created(memberTokenResponseDto);
    }


}

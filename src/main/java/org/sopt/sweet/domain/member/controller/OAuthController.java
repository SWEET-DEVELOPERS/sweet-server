package org.sopt.sweet.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberReissueTokenResponseDto;
import org.sopt.sweet.domain.member.service.OAuthService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OAuthController implements OAuthApi {
    private final OAuthService oAuthService;

    @GetMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<?>> kakaoLogin(@RequestParam("code") String code, @RequestHeader("X-Environment") String environment) {
        KakaoUserInfoResponseDto userInfo = oAuthService.kakaoCallback(code, environment);
        MemberTokenResponseDto memberToken = oAuthService.saveToken(userInfo.memberId());

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("userInfo", userInfo);
        loginResponse.put("memberToken", memberToken);

        return SuccessResponse.ok(loginResponse);
    }


    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<?>> logout(@UserId Long userId) {
        oAuthService.logout(userId);
        return SuccessResponse.ok("로그아웃 성공");
    }

    @PostMapping("/reissue")
    public ResponseEntity<SuccessResponse<?>> reissueToken(@RequestBody MemberTokenResponseDto memberTokenResponseDto) throws JsonProcessingException {
        MemberReissueTokenResponseDto memberToken = oAuthService.reissue(memberTokenResponseDto);
        return SuccessResponse.ok(memberToken);
    }

}

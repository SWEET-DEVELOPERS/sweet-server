package org.sopt.sweet.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.service.OAuthService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OAuthController implements OAuthApi {
    private final OAuthService oAuthService;

    @PostMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<?>> kakaoLogin(@RequestParam("code") String code) {
        //KakaoUserInfoResponseDto userInfo = oAuthService.kakaoCallback(code);
        //MemberTokenResponseDto memberToken = oAuthService.saveToken(userInfo.socialId());

        //Map<String, Object> loginResponse = new HashMap<>();
        //loginResponse.put("userInfo", userInfo);
        //loginResponse.put("memberToken", memberToken);
        //return SuccessResponse.ok(loginResponse);
        String response = "code"+code;
        return SuccessResponse.ok(response);

    }


    @PostMapping("/kakao/logout")
    public ResponseEntity<SuccessResponse<?>> kakaoLogout(@RequestHeader("Authorization") String accessToken,@RequestBody String socialId) {
        oAuthService.kakaoLogout(accessToken, socialId);
        return SuccessResponse.ok("로그아웃 성공");
    }



}

package org.sopt.sweet.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.reponse.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.reponse.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.service.OAuthService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OAuthController implements OAuthApi {
    private final OAuthService oAuthService;

    @PostMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<?>> kakaoLogin(@RequestParam("code") String code) {
        KakaoUserInfoResponseDto userInfo = oAuthService.kakaoCallback(code);
        MemberTokenResponseDto memberToken = oAuthService.saveToken(userInfo.socialId());

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("userInfo", userInfo);
        loginResponse.put("memberToken", memberToken);

        return SuccessResponse.ok(loginResponse);
    }


}

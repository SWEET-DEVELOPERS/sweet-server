package org.sopt.sweet.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.reponse.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.service.OAuthService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@RestController
public class OAuthController {
    private final OAuthService oAuthService;

    @PostMapping("/kakao/login")
    public ResponseEntity<SuccessResponse<?>> kakaoLogin(@RequestParam("code") String code) {
        KakaoUserInfoResponseDto userInfo = oAuthService.kakaoCallback(code);
        return SuccessResponse.ok(userInfo);
    }


}

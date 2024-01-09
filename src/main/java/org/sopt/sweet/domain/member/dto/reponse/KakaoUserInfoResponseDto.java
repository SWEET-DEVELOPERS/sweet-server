package org.sopt.sweet.domain.member.dto.reponse;


import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.entity.OAuthToken;


public record KakaoUserInfoResponseDto(
        Long socialId,
        String nickname,
        String profileImage,
        String accessToken,
        String refreshToken
) {


    KakaoUserInfoResponseDto of(Member member, OAuthToken oAuthToken) {
        return new KakaoUserInfoResponseDto(
                member.getSocialId(),
                member.getNickName(),
                member.getProfileImg(),
                oAuthToken.getAccessToken(),
                oAuthToken.getRefreshToken()
        );
    }


}
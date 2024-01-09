package org.sopt.sweet.domain.member.dto.reponse;


import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.entity.OAuthToken;


public record KakaoUserInfoResponseDto(
        Long memberId,
        Long socialId,
        String nickname,
        String profileImage
) {


    KakaoUserInfoResponseDto of(Member member) {
        return new KakaoUserInfoResponseDto(
                member.getId(),
                member.getSocialId(),
                member.getNickName(),
                member.getProfileImg()
        );
    }


}
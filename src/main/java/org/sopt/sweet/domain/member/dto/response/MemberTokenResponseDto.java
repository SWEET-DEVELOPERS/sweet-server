package org.sopt.sweet.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberTokenResponseDto(
        Long memberId,
        String accessToken,
        String refreshToken
) {
    public static MemberTokenResponseDto of(Long memberId,
                                            String accessToken,
                                            String refreshToken){
        return MemberTokenResponseDto.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

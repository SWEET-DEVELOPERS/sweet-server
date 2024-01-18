package org.sopt.sweet.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberReissueTokenResponseDto(
        String accessToken,
        String refreshToken
) {
    public static MemberReissueTokenResponseDto of(String newAccessToken, String newRefreshToken) {
        return MemberReissueTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}

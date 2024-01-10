package org.sopt.sweet.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberTokenResponseDto(
        String accessToken,
        String refreshToken
) {
    public static MemberTokenResponseDto of(String accessToken,
                                            String refreshToken) {
        return MemberTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

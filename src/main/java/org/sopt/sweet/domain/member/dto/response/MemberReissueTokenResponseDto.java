package org.sopt.sweet.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberReissueTokenResponseDto(
        String accessToken
) {
    public static MemberReissueTokenResponseDto of(String newAccessToken) {
        return MemberReissueTokenResponseDto.builder()
                .accessToken(newAccessToken)
                .build();
    }
}

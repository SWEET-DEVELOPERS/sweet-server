package org.sopt.sweet.domain.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "소셜로그인", description = "소셜로그인 관련 API")
public interface OAuthApi {

    @Operation(
            summary = "카카오 로그인",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/kakao/login")
    ResponseEntity<SuccessResponse<?>> kakaoLogin(
            @Parameter(
                    description = "redirect_uri로부터 받은 인가 코드",
                    required = true,
                    example = "gGMvN1u_dgHdTizP8uUf7HZHNls_3G4X8qbKTwihE0x5W6f3E6acGDDsc80KPXLrAAABjO-2eHHUNEQ5evY1pg"
            )
            @RequestParam("code") String code
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "로그아웃")
    @PostMapping("/api/oauth/kakao/logout")
    ResponseEntity<SuccessResponse<?>> logout(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "토큰 재발급")
    @PostMapping("/api/oauth/reissue")
    ResponseEntity<SuccessResponse<?>> reissueToken(
            @Parameter(
                    description = "만료된 access token, refresh token",
                    required = true,
                    example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"
            ) @RequestBody MemberTokenResponseDto memberTokenResponseDto
            ) throws JsonProcessingException;

}

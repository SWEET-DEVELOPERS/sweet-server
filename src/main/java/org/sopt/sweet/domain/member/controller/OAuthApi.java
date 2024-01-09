package org.sopt.sweet.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "카카오 로그아웃")
    @PostMapping("/api/oauth/kakao/logout")
    ResponseEntity<SuccessResponse<?>> kakaoLogout(
            @RequestHeader("Authorization") String accessToken,
            @Parameter(
                    description = "카카오 회원 고유 아이디",
                    required = true,
                    example = "123456789"
            )
            @RequestBody String socialId
    );

}

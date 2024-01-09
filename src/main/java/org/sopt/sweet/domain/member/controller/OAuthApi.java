package org.sopt.sweet.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "소셜로그인", description = "소셜로그인 관련 API")
public interface OAuthApi {


    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "카카오 로그인")
    @PostMapping("/api/oauth/kakao/login")
    ResponseEntity<SuccessResponse<?>> kakaoLogin(
            @RequestParam("code") String code
    );

}

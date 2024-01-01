package org.sopt.sweet.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "회원", description = "회원 관련 API")
public interface MemberApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "프로필을 조회")
    ResponseEntity<SuccessResponse<?>> testSwagger(
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "토큰 임시 발급 API입니다. 로그인 생성 시 삭제할 예정입니다.")
    ResponseEntity<SuccessResponse<?>> getToken(
            @Parameter(description = "멤버 고유 id", required = true) Long memberId
    );

}

package org.sopt.sweet.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.domain.member.dto.response.MyPageResponseDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "회원", description = "회원 관련 API")
public interface MemberApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @Operation(summary = "스웨거 테스트용 API")
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


    @Operation(
            summary = "종료된 선물방 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    @GetMapping("closed-room")
    public ResponseEntity<SuccessResponse<?>> getClosedRoom(
            @Parameter(
                    description = "Authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId
    );

    @Operation(
            summary = "진행중인 선물방 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    @GetMapping("active-room")
    public ResponseEntity<SuccessResponse<?>> getActiveRoom(
            @Parameter(
                    description = "Authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId
    );

    @Operation(
            summary = "마이페이지 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    @GetMapping("/mypage")
    public MyPageResponseDto getMyPage(
            @Parameter(
                    description = "Authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId
    );


}

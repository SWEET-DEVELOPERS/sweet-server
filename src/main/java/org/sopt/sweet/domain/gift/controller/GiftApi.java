package org.sopt.sweet.domain.gift.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.sweet.domain.gift.dto.request.CreateGiftRequestDto;
import org.sopt.sweet.domain.gift.dto.request.MyGiftsRequestDto;
import org.sopt.sweet.domain.gift.dto.request.TournamentScoreRequestDto;
import org.sopt.sweet.domain.gift.dto.response.TournamentRankingResponseDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "선물", description = "선물 관련 API")
public interface GiftApi {
    @Operation(
            summary = "새로운 선물 등록 API",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> createNewGift(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Valid @RequestBody CreateGiftRequestDto createGiftRequestDto
    );

    @Operation(
            summary = "내가 등록한 선물 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> getMyGift(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Valid @RequestBody MyGiftsRequestDto myGiftsRequestDto
    );

    @Operation(
            summary = "내가 등록한 선물 삭제 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> deleteMyGift(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @PathVariable Long giftId
    );


    @Operation(
            summary = "토너먼트 선물 리스트 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토너먼트 선물 목록 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "토너먼트나 사용자가 존재하지 않음",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "토너먼트 시작일이 지났거나 사용자가 방에 속해있지 않음",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> getTournamentGiftList(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Parameter(
                    description = "조회하려는 토너먼트가 진행 중인 방의 ID",
                    required = true,
                    example = "2"
            ) @PathVariable Long roomId
    );

    @Operation(
            summary = "선물 토너먼트 점수 등록 API",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> evaluateTournamentScore(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Valid @RequestBody TournamentScoreRequestDto tournamentScoreRequestDto
            );


    @Operation(
            summary = "토너먼트 정보 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "토너먼트나 사용자가 존재하지 않음",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "토너먼트 시작일이 지났거나 사용자가 방에 속해있지 않음",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    ResponseEntity<SuccessResponse<?>> getTournamentInfo(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Parameter(
                    description = "조회하려는 토너먼트가 진행 중인 방의 ID",
                    required = true,
                    example = "2"
            ) @PathVariable Long roomId
    );

    @Operation(
            summary = "토너먼트 랭킹 조회 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토너먼트 랭킹 조회 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "토너먼트나 사용자가 존재하지 않음",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "토너먼트 시작일이 지났거나 사용자가 방에 속해있지 않음",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "token")
    )
    @GetMapping("ranking/{roomId}")
    ResponseEntity<SuccessResponse<?>> getRanking(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @PathVariable Long roomId
    );
}

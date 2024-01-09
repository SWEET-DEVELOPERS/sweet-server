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
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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
}

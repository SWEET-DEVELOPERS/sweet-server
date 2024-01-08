package org.sopt.sweet.domain.room.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.JoinRoomRequestDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "선물방", description = "선물방 관련 API")
public interface RoomApi {

    @Operation(
            summary = "새로운 선물방 생성 API",
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
    ResponseEntity<SuccessResponse<?>> createNewRoom(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Valid @RequestBody CreateRoomRequestDto createRoomRequestDto
    );

    @Operation(
            summary = "선물방 초대 조회 API",
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
    ResponseEntity<SuccessResponse<?>> getRoomInviteInfo(
            @Parameter(
                    description = "room 초대코드",
                    required = true,
                    example = "1"
            ) @PathVariable String invitationCode
    );

    @Operation(
            summary = "선물방 참여 API",
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
    ResponseEntity<SuccessResponse<?>> joinRoom(
            @Parameter(
                    description = "authorization token에서 얻은 userId, 임의입력하면 대체됩니다.",
                    required = true,
                    example = "12345"
            ) @UserId Long userId,
            @Valid @RequestBody JoinRoomRequestDto joinRoomRequestDto
            );

}

package org.sopt.sweet.domain.gifter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.domain.gifter.dto.request.PresignedURLRequestDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "선물 모임", description = "선물 모임 관련 API")
public interface GifterApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
            }
    )
    @Operation(summary = "presigned URL 발급 API")
    ResponseEntity<SuccessResponse<?>> getPresignedURL(
            @Parameter(
                    description = "request dto",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PresignedURLRequestDto.class,
                                    required = true),
                            mediaType = "application/json"
                    )
            ) PresignedURLRequestDto presignedURLRequestDto
    );
}

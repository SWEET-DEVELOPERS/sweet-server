package org.sopt.sweet.domain.opengraph.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.domain.opengraph.dto.OpengraphRequestDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "오픈그래프", description = "오픈그래프 관련 API")
public interface OpengraphAPI {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
            }
    )
    @Operation(summary = "오픈그래프 탐색 API")
    ResponseEntity<SuccessResponse<?>> getOpenGraph(
            @Parameter(
                    description = "request dto",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = OpengraphRequestDto.class,
                                    required = true),
                            mediaType = "application/json"
                    )
            ) OpengraphRequestDto opengraphRequestDto
    );
}

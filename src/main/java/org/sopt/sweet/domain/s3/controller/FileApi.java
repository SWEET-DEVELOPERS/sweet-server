package org.sopt.sweet.domain.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.sweet.domain.s3.dto.request.PresignedUrlRequestDto;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "파일", description = "S3 관련 API")
public interface FileApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201"),
            }
    )
    @Operation(summary = "presigned URL 발급 API")
    ResponseEntity<SuccessResponse<?>> getPresignedURL(
            @Parameter(
                    description = "생성하고자 하는 파일 이름",
                    required = true,
                    content = @Content(
                            mediaType = "application/json"
                    )
            ) String fileName
    );
}

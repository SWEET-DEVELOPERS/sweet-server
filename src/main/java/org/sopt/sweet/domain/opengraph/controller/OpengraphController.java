package org.sopt.sweet.domain.opengraph.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.opengraph.dto.OpengraphResponseDto;
import org.sopt.sweet.domain.opengraph.dto.OpengraphRequestDto;
import org.sopt.sweet.domain.opengraph.service.OpengraphService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/opengraph")
@RestController
public class OpengraphController implements OpengraphAPI{

    private final OpengraphService opengraphService;

    @ResponseBody
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getOpenGraph(@RequestBody OpengraphRequestDto opengraphRequestDto) {
        OpengraphResponseDto opengraphResponseDto = opengraphService.getData(opengraphRequestDto);
        return SuccessResponse.ok(opengraphResponseDto);
    }

}
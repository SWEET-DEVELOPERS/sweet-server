package org.sopt.sweet.domain.opengraph.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.opengraph.dto.OpengraphResponseDto;
import org.sopt.sweet.domain.opengraph.dto.URLRequestDto;
import org.sopt.sweet.domain.opengraph.service.OpengraphService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/opengraph")
@RestController
public class OpengraphController {

    private final OpengraphService opengraphService;

    @ResponseBody
    @GetMapping(value = "")
    public ResponseEntity<SuccessResponse<?>> getOpenGraph(@RequestBody URLRequestDto urlRequestDto) {
        OpengraphResponseDto opengraphResponseDto = opengraphService.getData(urlRequestDto);
        return SuccessResponse.ok(opengraphResponseDto);
    }

}
package org.sopt.sweet.domain.gifter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gifter")
@RestController
public class GifterController implements GifterApi {
}

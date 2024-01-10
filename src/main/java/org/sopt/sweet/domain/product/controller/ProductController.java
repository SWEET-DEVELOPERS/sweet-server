package org.sopt.sweet.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.product.dto.response.HotProductsResponseDto;
import org.sopt.sweet.domain.product.service.ProductService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    @GetMapping("/hot")
    public ResponseEntity<SuccessResponse<?>> getHotGift(@UserId Long userId) {
        final HotProductsResponseDto hotProductsResponseDto = productService.getHotGift(userId);
        return SuccessResponse.ok(hotProductsResponseDto);
    }
}

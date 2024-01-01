package org.sopt.sweet.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/product")
@RestController
public class ProductController implements ProductApi{
}

package org.sopt.sweet.domain.gift.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/gift")
@RestController
public class GiftController implements GiftApi{
}

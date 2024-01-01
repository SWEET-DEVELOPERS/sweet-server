package org.sopt.sweet.domain.member.controller;

import org.sopt.sweet.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/member")
@RestController
public class MemberController implements MemberApi{

    @GetMapping(path = "/test")
    public ResponseEntity<SuccessResponse<?>> testSwagger() {
        String response = "Sweet little kitty";
        return SuccessResponse.ok(response);
    }
}

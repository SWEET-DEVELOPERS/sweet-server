package org.sopt.sweet.global.common;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {
    @Hidden
    @RequestMapping("/")
    public String SweetServer() {
        return "Sweet little kitty!";
    }
}
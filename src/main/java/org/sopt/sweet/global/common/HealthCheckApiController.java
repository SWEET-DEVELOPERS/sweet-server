package org.sopt.sweet.global.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {
    @RequestMapping("/")
    public String SweetServer() {
        return "Sweet little kitty!";
    }
}

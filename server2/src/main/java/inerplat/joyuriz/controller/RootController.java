package inerplat.joyuriz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/gateway/hcheck")
    public String healthCheck() {
        return "Health Check OK!";
    }
}

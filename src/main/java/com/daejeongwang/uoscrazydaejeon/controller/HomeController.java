package com.daejeongwang.uoscrazydaejeon.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping("/")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Swagger UI로 이동", content = @Content)
    })
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }
}

package com.khit.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/auth/accessDenied")
    public String access(){
        return "/auth/accessDenied";
    }
}

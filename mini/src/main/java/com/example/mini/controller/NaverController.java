package com.example.mini.controller;

import com.example.mini.dto.NaverDTO;
import com.example.mini.entity.MsgEntity;
import com.example.mini.service.NaverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverController {
    private final NaverService naverService;

    @GetMapping("/oauth2/login/naver")
    public ResponseEntity<MsgEntity> callback(HttpServletRequest request) throws Exception{
        NaverDTO naverInfo = naverService.getNaverInfo(request.getParameter("code"));
        return ResponseEntity.ok()
                .body(new MsgEntity("Success", naverInfo));
    }
}

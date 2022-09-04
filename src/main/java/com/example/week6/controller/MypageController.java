package com.example.week6.controller;


import com.example.week6.controller.response.ResponseDto;
import com.example.week6.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;


    @GetMapping("/api/mypage")
    public ResponseDto<?> getMyActs(HttpServletRequest request) {
        return mypageService.getAllMyActs(request);
    }
}

package com.example.adminbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        // 루트 접속 시 login.html로 이동
        return "login";
    }
}
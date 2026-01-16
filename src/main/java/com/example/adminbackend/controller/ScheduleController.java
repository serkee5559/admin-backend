package com.example.adminbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleController {

    @GetMapping("/schedule") // 브라우저에서 접속할 주소
    public String schedulePage(Model model) {
        model.addAttribute("menu", "schedule");
        return "calendar/main"; // templates/calendar/main.html을 실행
    }
}
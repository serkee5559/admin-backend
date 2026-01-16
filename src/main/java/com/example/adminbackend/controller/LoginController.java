package com.example.adminbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        // LoginController 내부 로그인 메서드 일부
        if ("guest".equals(username) && "guest1234".equals(password)) {
            session.setAttribute("user", username); // 이 부분이 있어야 인터셉터를 통과합니다.
            return "redirect:/dashboard"; // 로그인 후 대시보드로 이동
        }

        // 로그인 실패 시 메시지와 함께 다시 로그인 페이지로
        redirectAttributes.addFlashAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }
}
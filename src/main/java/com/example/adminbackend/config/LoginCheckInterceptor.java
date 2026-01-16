package com.example.adminbackend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // 세션에 'user' 정보가 없는 경우
        if (session == null || session.getAttribute("user") == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            // 자바스크립트로 안내 메시지를 띄우고 로그인(/) 페이지로 이동
            out.println("<script>alert('로그인이 필요한 서비스입니다.'); location.href='/';</script>");
            out.flush();
            out.close();
            return false; // 컨트롤러 진입 차단
        }

        return true; // 로그인 되어 있으면 통과
    }
}
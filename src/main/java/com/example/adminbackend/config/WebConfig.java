package com.example.adminbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .order(1) // 우선순위
                .addPathPatterns("/**") // 모든 경로에 적용하되
                .excludePathPatterns(
                        "/", "/login", "/logout", // 로그인 관련 경로는 제외
                        "/css/**", "/js/**", "/images/**", "/*.ico", // 정적 리소스 제외
                        "/error"
                );
    }
}
package com.example.adminbackend;// ... 기존 import 생략 ...
//import com.example.adminbackend.domain.Member;
//import com.example.adminbackend.service.MemberService;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminBackendApplication.class, args);
    }
}
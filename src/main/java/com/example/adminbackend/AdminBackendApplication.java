package com.example.adminbackend;// ... 기존 import 생략 ...
import com.example.adminbackend.domain.Member;
import com.example.adminbackend.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AdminBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBackendApplication.class, args);
    }

    // 서버가 켜질 때 실행되는 테스트 데이터 삽입 코드
//    @Bean
//    public CommandLineRunner demo(MemberService memberService) {
//        return (args) -> {
//            Member testMember = new Member();
//            testMember.setName("홍길동");
//            testMember.setEmail("hong@test.com");
//            testMember.setDepartment("개발팀");
//
//            memberService.join(testMember);
//            System.out.println("### 테스트 데이터 삽입 완료! ###");
//        };
//    }
}
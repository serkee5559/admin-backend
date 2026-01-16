package com.example.adminbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // 이 클래스는 DB 테이블과 매핑됨을 선언합니다.
@Getter @Setter // Lombok이 자동으로 getter, setter를 만들어줍니다.
public class Member {

    @Id // 기본키(Primary Key) 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 자동 증가 (Auto Increment)
    private Long id;

    @Column(nullable = false) // 필수값 설정
    private String name; // 이름

    private String email; // 이메일

    private String department; // 부서명
}
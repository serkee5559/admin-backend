package com.example.adminbackend.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    private String name;
    private String email;      // 추가
    private String department; // 추가
}
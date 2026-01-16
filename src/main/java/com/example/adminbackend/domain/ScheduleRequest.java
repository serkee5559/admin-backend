package com.example.adminbackend.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class ScheduleRequest {
    private Long id;
    private String title;
    private String category;
    private String start; // JS에서 오는 문자열을 받기 위해 String 사용
    private String end;
    private String color;
    private List<Long> participantIds; // 참석자 ID 리스트
}
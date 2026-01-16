package com.example.adminbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 사원 한 명이 여러 번 출근하므로 다대일 관계
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime checkInTime;  // 출근 시간
    private LocalDateTime checkOutTime; // 퇴근 시간
    private String status;              // 출근, 지각, 퇴근 등 상태
    private LocalDate attendanceDate; // [추가] 근태 관리 기준 날짜

    public String getStatus() {
        // 09:00:00 이후 출근이면 지각으로 표시 (출근 상태일 때만 체크)
        if (this.checkInTime != null && this.checkInTime.getHour() >= 9 && this.checkInTime.getMinute() > 0) {
            return "지각";
        }
        return status; // 기존 상태(출근/퇴근) 반환
    }

    // 근무 시간 계산 메서드 (시간 단위 반환)
    public long getWorkingHours() {
        if (this.checkInTime != null && this.checkOutTime != null) {
            // 출근과 퇴근 사이의 시간을 '시간' 단위로 계산
            return java.time.Duration.between(this.checkInTime, this.checkOutTime).toHours();
        }
        return 0;
    }
}
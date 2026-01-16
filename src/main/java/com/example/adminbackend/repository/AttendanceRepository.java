package com.example.adminbackend.repository;
import com.example.adminbackend.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // 특정 멤버의 최신 근태 기록을 찾는 기능 등이 나중에 추가될 수 있습니다.
}
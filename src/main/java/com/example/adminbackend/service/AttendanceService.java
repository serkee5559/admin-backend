package com.example.adminbackend.service;

import com.example.adminbackend.domain.Attendance;
import com.example.adminbackend.domain.Member;
import com.example.adminbackend.repository.AttendanceRepository;
import com.example.adminbackend.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    // 출근 기록
    @Transactional
    public void checkIn(Long memberId, LocalDate selectedDate) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setAttendanceDate(selectedDate); // [필수] 화면에서 넘어온 날짜 저장
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus("출근");

        attendanceRepository.save(attendance);
    }

    // AttendanceService.java 에 추가/수정
    @Transactional
    public void checkOut(Long memberId, LocalDate date) {
        // 1. 해당 날짜(date)와 회원(memberId)에 매칭되는 기록 중 퇴근 안 한 것을 찾음
        List<Attendance> attendances = attendanceRepository.findAll();
        Attendance targetAttendance = attendances.stream()
                .filter(a -> a.getMember().getId().equals(memberId)
                        && a.getAttendanceDate() != null
                        && a.getAttendanceDate().equals(date) // [중요] 보고 있는 날짜와 일치해야 함
                        && a.getCheckOutTime() == null)       // 아직 퇴근 안 한 기록
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 날짜에 대한 출근 기록이 없습니다."));

        // 2. 데이터 업데이트
        targetAttendance.setCheckOutTime(LocalDateTime.now());
        targetAttendance.setStatus("퇴근");

        // @Transactional이 붙어있으므로 별도의 save 호출 없이도 메서드 종료 시 DB에 반영됩니다.
    }

    // 오늘 모든 인원의 근태 현황 가져오기
    public List<Attendance> findTodayAttendance() {
        return attendanceRepository.findAll(); // 실제로는 오늘 날짜만 필터링하는 쿼리가 좋습니다.
    }

    // AttendanceService.java 에 추가
    public Map<Long, Attendance> getTodayAttendanceMap() {
        List<Attendance> list = attendanceRepository.findAll(); // 실제론 오늘 날짜만 가져오도록 쿼리 작성 권장
        return list.stream()
                .collect(Collectors.toMap(
                        a -> a.getMember().getId(),
                        a -> a,
                        (existing, replacement) -> replacement // 중복 시 최신 데이터 선택
                ));
    }

    // 특정 날짜의 데이터를 Map으로 변환할 때 기준 날짜 필터링 필수
    public Map<Long, Attendance> getAttendanceMapByDate(LocalDate date) {
        return attendanceRepository.findAll().stream()
                .filter(a -> a.getAttendanceDate() != null && a.getAttendanceDate().equals(date))
                .collect(Collectors.toMap(
                        a -> a.getMember().getId(),
                        a -> a,
                        (existing, replacement) -> replacement // 중복 시 최신본
                ));
    }
}
package com.example.adminbackend.controller;

import com.example.adminbackend.domain.Attendance;
import com.example.adminbackend.repository.ScheduleRepository;
import com.example.adminbackend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final AttendanceService attendanceService;
    private final ScheduleRepository scheduleRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("menu", "dashboard");
        LocalDate today = LocalDate.now();

        // 1. 상단 통계 (오늘 날짜 맵 조회)
        var todayAttendance = attendanceService.getAttendanceMapByDate(today);
        long presentCount = todayAttendance.values().stream()
                .filter(a -> "출근".equals(a.getStatus())).count();
        long lateCount = todayAttendance.values().stream()
                .filter(a -> "지각".equals(a.getStatus())).count();

        model.addAttribute("presentCount", presentCount);
        model.addAttribute("lateCount", lateCount);
        model.addAttribute("recentSchedules", scheduleRepository.findAll());

        // 2. 그래프 데이터 (최근 7일)
        List<String> days = new ArrayList<>();
        List<Long> counts = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            days.add(date.format(DateTimeFormatter.ofPattern("MM-dd")));
            long count = attendanceService.getAttendanceMapByDate(date).values().stream()
                    .filter(a -> a.getCheckInTime() != null).count();
            counts.add(count);
        }
        model.addAttribute("chartLabels", days);
        model.addAttribute("chartData", counts);

        // 3. 실시간 출근 현황 (getInTime 대신 getCheckInTime 사용)
        var recentAttendees = todayAttendance.values().stream()
                .filter(a -> a.getCheckInTime() != null)
                .sorted(Comparator.comparing(Attendance::getCheckInTime).reversed())
                .limit(5)
                .map(a -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("name", a.getMember().getName());
                    map.put("dept", a.getMember().getDepartment());
                    map.put("time", a.getCheckInTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                    return map;
                })
                .collect(Collectors.toList());
        model.addAttribute("recentAttendees", recentAttendees);

        return "dashboard/main";
    }
}
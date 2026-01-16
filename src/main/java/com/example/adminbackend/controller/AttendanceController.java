package com.example.adminbackend.controller;

import com.example.adminbackend.service.AttendanceService;
import com.example.adminbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <--- 이 임포트가 반드시 이 경로여야 합니다!
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final MemberService memberService;

    @GetMapping("/attendance")
    public String attendanceList(@RequestParam(value = "date", required = false) String dateStr, Model model) {
        LocalDate selectedDate = (dateStr == null) ? LocalDate.now() : LocalDate.parse(dateStr);

        model.addAttribute("menu", "attendance");
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("prevDate", selectedDate.minusDays(1));
        model.addAttribute("nextDate", selectedDate.plusDays(1));
        model.addAttribute("members", memberService.findMembers());
        model.addAttribute("attendanceMap", attendanceService.getAttendanceMapByDate(selectedDate));

        return "attendance/list";
    }

    @PostMapping("/attendance/checkin/{id}")
    public String checkIn(@PathVariable("id") Long id, @RequestParam("date") String date) {
        attendanceService.checkIn(id, LocalDate.parse(date)); // 날짜 정보 전달
        return "redirect:/attendance?date=" + date; // 처리 후 보던 날짜로 복귀
    }

    @PostMapping("/attendance/checkout/{id}")
    public String checkOut(@PathVariable("id") Long id, @RequestParam("date") String date) {
        // 넘겨받은 날짜 문자열을 LocalDate로 변환하여 전달
        attendanceService.checkOut(id, LocalDate.parse(date));
        return "redirect:/attendance?date=" + date;
    }
}
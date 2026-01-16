package com.example.adminbackend.controller;

import com.example.adminbackend.domain.Member;
import com.example.adminbackend.domain.Schedule;
import com.example.adminbackend.domain.ScheduleRequest;
import com.example.adminbackend.repository.MemberRepository;
import com.example.adminbackend.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleApiController {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    // 달력 데이터 불러오기
    @GetMapping
    public List<Map<String, Object>> getSchedules() {
        return scheduleRepository.findAll().stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getId());
            map.put("title", "[" + s.getCategory() + "] " + s.getTitle());
            map.put("start", s.getStart());
            map.put("end", s.getEnd());
            map.put("color", s.getColor());

            // [추가] 참석자 정보를 Map에 담아 프론트엔드로 보냅니다.
            if (s.getParticipants() != null) {
                List<Map<String, Object>> participantList = s.getParticipants().stream().map(p -> {
                    Map<String, Object> pMap = new HashMap<>();
                    pMap.put("id", p.getId());
                    pMap.put("name", p.getName());
                    pMap.put("dept", p.getDepartment());
                    return pMap;
                }).collect(Collectors.toList());
                map.put("participants", participantList);
            }

            return map;
        }).collect(Collectors.toList());
    }

    // 새로운 일정 저장 (참석자 포함)
    @PostMapping
    public Schedule saveSchedule(@RequestBody ScheduleRequest request) {
        // 1. 기존 데이터 조회 또는 신규 생성 (파라미터명과 변수명 충돌 해결)
        Schedule schedule = (request.getId() != null)
                ? scheduleRepository.findById(request.getId()).orElse(new Schedule())
                : new Schedule();

        // 2. 기본 정보 설정 (request 객체 사용)
        schedule.setTitle(request.getTitle());
        schedule.setCategory(request.getCategory());
        schedule.setColor(request.getColor());

        // 3. 날짜 포맷 변환 (T 처리)
        if (request.getStart() != null && !request.getStart().isEmpty()) {
            schedule.setStart(LocalDateTime.parse(request.getStart().replace(" ", "T")));
        }
        if (request.getEnd() != null && !request.getEnd().isEmpty()) {
            schedule.setEnd(LocalDateTime.parse(request.getEnd().replace(" ", "T")));
        }

        // 4. 참석자 처리 (ID 리스트 -> Member 객체 리스트로 변환하여 저장)
        if (request.getParticipantIds() != null && !request.getParticipantIds().isEmpty()) {
            List<Member> participants = memberRepository.findAllById(request.getParticipantIds());
            schedule.setParticipants(participants);
        } else {
            // 참석자가 없는 경우 기존 리스트 비우기
            if (schedule.getParticipants() != null) {
                schedule.getParticipants().clear();
            }
        }

        return scheduleRepository.save(schedule);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable("id") Long id) {
        scheduleRepository.deleteById(id);
    }
}
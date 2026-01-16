package com.example.adminbackend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    @Column(name = "start_time")
    private LocalDateTime start;
    @Column(name = "end_time")
    private LocalDateTime end;
    private String color;

    // [중요] 이 설정이 있어야 DB에 'schedule_participants' 테이블이 생성되고 저장됩니다.
    @ManyToMany
    @JoinTable(
            name = "schedule_participants",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<Member> participants = new ArrayList<>();
}
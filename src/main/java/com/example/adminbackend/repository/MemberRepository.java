package com.example.adminbackend.repository;

import com.example.adminbackend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // JpaRepository를 상속받는 것만으로도
    // save(), findAll(), findById() 같은 기본 기능이 다 생깁니다!
}
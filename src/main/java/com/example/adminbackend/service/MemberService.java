package com.example.adminbackend.service;

import com.example.adminbackend.domain.Member;
import com.example.adminbackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // Repository를 자동으로 연결해줍니다.
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 저장 (가입)
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // MemberService.java에 추가
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Transactional
    public void updateMember(Long id, String name, String email, String department) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        // 변경 감지(Dirty Checking) 기능을 사용하여 DB를 업데이트합니다.
        member.setName(name);
        member.setEmail(email);
        member.setDepartment(department);
    }
}


package com.example.adminbackend.controller;

import com.example.adminbackend.domain.Member;
import com.example.adminbackend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // RestController에서 변경
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 데이터만 보낼 때 (기존 주소)
    @GetMapping("/api/members")
    @ResponseBody
    public List<Member> list() {
        return memberService.findMembers();
    }

    // HTML 화면을 보여줄 때
    @GetMapping("/members")
    public String memberList(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); // 데이터를 "members"라는 이름으로 담아서 보냄
        model.addAttribute("menu", "members"); // 현재 메뉴 이름을 추가!
        return "members/memberList"; // templates/members/memberList.html 파일을 찾아감
    }



    // 1. 등록 폼 화면으로 이동
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    // 2. 실제 데이터를 받아서 저장
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setEmail(form.getEmail());           // 이 줄이 있는지 확인!
        member.setDepartment(form.getDepartment()); // 이 줄이 있는지 확인!

        memberService.join(member);

        return "redirect:/members"; // 저장 후 목록 화면으로 자동 이동
    }

    // MemberController.java에 추가
    @PostMapping("/members/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id); // 서비스 호출하여 삭제 실행
        return "redirect:/members";    // 삭제 후 목록으로 다시 이동
    }

    // 1. 수정 폼 이동: 기존 회원의 데이터를 모델에 담아 화면으로 보냅니다.
    @GetMapping("/members/edit/{id}")
    public String updateMemberForm(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findMembers().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("member", member);
        return "members/updateMemberForm";
    }

    // 2. 수정 실행: 수정된 데이터를 받아 DB를 업데이트합니다.
    @PostMapping("/members/edit/{id}")
    public String updateMember(@PathVariable("id") Long id, @ModelAttribute("member") Member member) {
        memberService.updateMember(id, member.getName(), member.getEmail(), member.getDepartment());
        return "redirect:/members";
    }

}
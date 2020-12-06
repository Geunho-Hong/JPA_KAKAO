package com.jpa.kakao.controller;

import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public void insertMember(@RequestBody Member member){
        memberService.joinMember(member);
    }


}

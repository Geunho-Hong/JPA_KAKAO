package com.jpa.kakao.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void joinMember(Member member){
        memberRepository.save(member);
    }

}

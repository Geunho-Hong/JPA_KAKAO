package com.jpa.kakao.service.Impl;

import com.jpa.kakao.domain.Member;
import com.jpa.kakao.repository.MemberRepository;
import com.jpa.kakao.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public int insertMember(Member member) {
        memberRepository.save(member);
        return 1;
    }

}

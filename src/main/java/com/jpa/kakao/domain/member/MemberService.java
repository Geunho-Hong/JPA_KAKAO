package com.jpa.kakao.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long insertMember(Member member){
        memberRepository.save(member);
        return member.getMemberNo();
    }

    public Member selectMember(Long memberNo){
        return memberRepository
                .findById(memberNo)
                .orElseThrow(() -> new EntityNotFoundException(memberNo + "is not exist"));
    }

}

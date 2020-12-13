package com.jpa.kakao.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member insertMember(Member member){
        // Email이나 Account 기능 추가
        return memberRepository.save(member);
    }

    public Member selectMember(Long memberNo){
        return memberRepository
                .findById(memberNo)
                .orElseThrow(() -> new EntityNotFoundException(memberNo + "is not exist"));
    }

}

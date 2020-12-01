package com.jpa.kakao.service;

import com.jpa.kakao.domain.Member;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    public abstract int insertMember(Member member);

}

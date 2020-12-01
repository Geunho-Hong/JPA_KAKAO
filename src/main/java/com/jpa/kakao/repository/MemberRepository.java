package com.jpa.kakao.repository;

import com.jpa.kakao.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

}

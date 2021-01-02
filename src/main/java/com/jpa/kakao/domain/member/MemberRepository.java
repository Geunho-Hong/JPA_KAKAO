package com.jpa.kakao.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);

    boolean existsByMemberId(String memberId);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Member> findByMemberId(String memberId);

}

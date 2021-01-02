/*
package com.jpa.kakao.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    //private final EntityManager entityManager;
    //private final JpaRepository jpaRepository;

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        Optional<Member> member = findByMemberId(memberId);
        return Optional.ofNullable(member);
    }


}
*/

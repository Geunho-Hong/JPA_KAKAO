package com.jpa.kakao.domain.friend;

import com.jpa.kakao.domain.FriendRelationShip;
import com.jpa.kakao.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendRelationShip,Long> {

}

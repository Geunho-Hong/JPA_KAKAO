package com.jpa.kakao.domain;

import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.support.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
@Entity(name = "friend_relationship_tbl")
public class FriendRelationShip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relationship_no")
    private Long relationShipNo;

    // JPA는 상대 테이블의 PK를 멤버변수로 갖지 않고, 엔티티 자체를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member memberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", unique = true)
    private Member friendId;

    @Column(name = "friend_name")
    private String friendName;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

}

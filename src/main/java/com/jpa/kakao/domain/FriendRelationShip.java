package com.jpa.kakao.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity(name = "FriendRelationShip")
public class FriendRelationShip extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long relationShipNo;

    // JPA는 상대 테이블의 PK를 멤버변수로 갖지 않고, 엔티티 자체를 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member memberNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendNo")
    private Member friendId;

    private String friendName;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

}

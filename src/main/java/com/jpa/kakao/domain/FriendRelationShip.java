package com.jpa.kakao.domain;

import com.jpa.kakao.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
@AllArgsConstructor
@Entity(name = "friend_relationship_tbl")
public class FriendRelationShip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relationship_no")
    private Long relationShipNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "friend_no", unique = true)
    private Member friend;

    @Column(name = "friend_name")
    private String friendName;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    public static FriendRelationShip addFriend(Member member, Member friend){

        FriendRelationShip friendRelationShip = FriendRelationShip.builder()
                .member(member)
                .friend(friend)
                .friendName(friend.getName())
                .status(FriendStatus.NORMAL)
                .build();

        member.getFriendRelationShipList().add(friendRelationShip);

        return friendRelationShip;
    }
}

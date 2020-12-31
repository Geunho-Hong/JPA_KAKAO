package com.jpa.kakao.domain;

import com.jpa.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
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
    @JoinColumn(name = "friend_id", unique = true)
    private Member friendId;

    @Column(name = "friend_name")
    private String friendName;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

}

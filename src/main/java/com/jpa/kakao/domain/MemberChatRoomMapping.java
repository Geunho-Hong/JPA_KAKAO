package com.jpa.kakao.domain;

import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.support.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
@Entity(name ="member_chatroom_tbl")
public class MemberChatRoomMapping extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column (name = "member_chatroom_no")
    private Long memberChatRoomNo; // 고유한 멤버 매핑 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="chatroom_no")
    private ChatRoom chatRoom;

    @Column(name = "room_name")
    private String roomName; // 대화방 이름

    @Column(name ="join_date")
    private LocalDateTime joinDate; // 방 참석 시간

}

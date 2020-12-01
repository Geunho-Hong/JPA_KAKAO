package com.jpa.kakao.domain;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name ="member_chatroom")
public class MemberChatRoomMapping extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long memberChatRoomNo; // 고유한 멤버 매핑 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberNo")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="chatroomNo")
    private ChatRoom chatRoom;

    private String roomName; // 대화방 이름

    private LocalDateTime joinDate; // 방 참석 시간

}

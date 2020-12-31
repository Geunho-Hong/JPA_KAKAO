package com.jpa.kakao.domain;

import com.jpa.kakao.domain.support.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Getter
@Entity(name ="chat_tbl")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long chatId ; // 고유한 채팅 번호

    @ManyToOne
    @JoinColumn(name = "chatroom_no")
    private ChatRoom chatRoom; // 고유한 대화방 no (fk)

    @Column(name ="member_no")
    private Long memberNo; // 메세지를 작성한 회원 번호

    @Column(name = "chat_message")
    private String chatMessage; // 채팅 내용

    @ColumnDefault("0")
    @Column(name = "chat_status")
    private String status;  // 채팅 삭제 여부

}

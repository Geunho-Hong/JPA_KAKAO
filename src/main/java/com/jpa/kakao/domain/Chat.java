package com.jpa.kakao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Getter
@Setter
@Entity(name ="chat")
public class Chat extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long chatId ; // 고유한 채팅 번호

    private Long memberNo; // 메세지를 작성한 회원 번호

    private String message; // 유저 메세지

    @ColumnDefault("N")
    private String status;  // 채팅 삭제 여부

    @ManyToOne
    @JoinColumn(name = "chatroom_no")
    private ChatRoom chatRoom; // 고유한 대화방 no (fk)

    @Builder
    public Chat(Long chatId , Long memberNo , String message){
        this.chatId = chatId;
        this.memberNo = memberNo;
        this.message = message;
    }

}

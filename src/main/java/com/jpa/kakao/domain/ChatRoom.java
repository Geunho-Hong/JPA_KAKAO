package com.jpa.kakao.domain;

import com.jpa.kakao.domain.support.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
@Entity(name = "chat_room_tbl")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "chatroom_no")
    private Long chatRoomNo;   // 고유한 대화방 번호

    @Column(name = "chatroom_status")
    @ColumnDefault("0")
    private String chatRoomStatus;

    @OneToMany(mappedBy = "chatRoom")
    List<Chat> chatList = new ArrayList<>();

}

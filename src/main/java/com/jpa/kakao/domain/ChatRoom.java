package com.jpa.kakao.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity(name = "chatRoom")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long chatRoomNo;   // 고유한 대화방 번호

    @OneToMany(mappedBy = "chatRoom")
    List<Chat> chatList = new ArrayList<>();

}

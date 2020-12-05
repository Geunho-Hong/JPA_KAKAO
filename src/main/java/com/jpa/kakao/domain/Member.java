package com.jpa.kakao.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity(name ="member_tbl")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Proxy 생성을 위한 기본 생성자
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_no")
    private Long memberNo; // 유저 고유 번호

    @Column(nullable = false, unique = true, name = "kakao_id")
    private String kakaoId;    // 유저 카카오 Id

    @Column(nullable = false , name = "password")
    private String password;  // 유저 패스워드

    @Column(nullable = false,unique = true)
    private String email;   // 유저 이메일

    @Column(nullable = false)
    private String name;    // 유저이름

    @Column(nullable = false,unique = true, name = "phone_number")
    private String phoneNumber;    // 유저 핸드폰 번호

    @Column(name = "status_message")
    private String statusMessage; // 유저 상태 메세지

    @Column(name = "profile_url")
    private String profileUrl; // 유저 프로필 이미지

    @ColumnDefault("0")
    private String status;  // 유저 탈퇴 여부

    @Column(name = "birth_date")
    private LocalDateTime birthDate;    // 생일

}

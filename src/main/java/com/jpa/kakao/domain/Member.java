package com.jpa.kakao.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name ="member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long memberNo; // 유저 고유 번호

    @Column(nullable = false, unique = true)
    private String kakaoId;    // 유저 카카오 Id

    @Column(nullable = false)
    private String password;  // 유저 패스워드

    @Column(nullable = false,unique = true)
    private String email;   // 유저 이메일

    @Column(nullable = false)
    private String name;    // 유저이름

    @Column(nullable = false,unique = true)
    private String phoneNumber;    // 유저 핸드폰 번호

    private String statusMessage; // 유저 상태 메세지

    private String profileUrl; // 유저 프로필 이미지

    @ColumnDefault("N")
    private String status;  // 유저 탈퇴 여부

    private LocalDateTime birthDate;    // 생일

}

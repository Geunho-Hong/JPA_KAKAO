package com.jpa.kakao.dto.member;

import com.jpa.kakao.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class MemberSignUpDto {

    private String kakaoId;

    private String password;

    private String email;

    private String name;

    private String phoneNumber;

    private LocalDateTime birthDate;

    public Member toMemberEntity(){
        return Member.builder()
                .kakaoId(kakaoId)
                .password(password)
                .email(email)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .build();
    }

}

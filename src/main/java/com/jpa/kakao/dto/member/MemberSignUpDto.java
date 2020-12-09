package com.jpa.kakao.dto.member;

import com.jpa.kakao.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class MemberSignUpDto {

    // @NotNull : 반드시 값이 있어야 한다
    // @NotEmpty : 반드시 값이 존재하고 길이 혹은 크기가 0보다 커야한다
    // @NotBrank : 반드시 값이 존재하고 공백 문자를 제외한 길이가 0보다 커야 한다

    @NotEmpty
    private String kakaoId;

    @NotEmpty
    private String password;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private LocalDateTime birthDate;

    public Member toMemberEntity() {
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

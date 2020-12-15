package com.jpa.kakao.dto.member;

import com.jpa.kakao.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class MemberSignUpDto {

    @NotEmpty(message =  "카카오 Id를 입력해주세요")
    private String kakaoId;

    @NotEmpty(message = "패스워드를 입력해주세요")
    private String password;

    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    @NotNull(message = "생년월일을 입력해주세요")
    private LocalDate birthDate;

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

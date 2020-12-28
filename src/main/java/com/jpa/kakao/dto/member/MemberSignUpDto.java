package com.jpa.kakao.dto.member;

import com.jpa.kakao.common.validator.BirthDate;
import com.jpa.kakao.common.validator.PhoneNumber;
import com.jpa.kakao.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpDto {

    @NotEmpty(message =  "카카오 Id를 입력해주세요")
    private String kakaoId;

    @NotEmpty(message = "패스워드를 입력해주세요")
    private String password;

    @Email
    @NotEmpty(message = "이메일을 입력해주세요")
    private String email;

    @NotEmpty(message = "이름을 입력해주세요")
    @Size(min = 2, max = 10)
    private String name;

    @PhoneNumber
    private String phoneNumber;

    @BirthDate
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

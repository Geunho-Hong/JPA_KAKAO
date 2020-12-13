package com.jpa.kakao.dto.member;

import com.jpa.kakao.domain.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDto {

    private final Long memberNo;
    private final String name;
    private final String email;
    private final String statusMessage;
    private final String phoneNumber;
    private final String profileUrl;
    private final String kakaoId;
    private final LocalDateTime birthDate;

    public static MemberResponseDto toMemberDto(Member member){
        return MemberResponseDto.builder()
                .memberNo(member.getMemberNo())
                .name(member.getName())
                .email(member.getEmail())
                .statusMessage(member.getStatusMessage())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .kakaoId(member.getKakaoId())
                .birthDate(member.getBirthDate())
                .build();
    }

}

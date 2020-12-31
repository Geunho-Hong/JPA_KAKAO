package com.jpa.kakao.controller;

import com.jpa.kakao.common.ApiResponse;
import com.jpa.kakao.common.StatusEnum;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.dto.member.MemberResponseDto;
import com.jpa.kakao.dto.member.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponseDto> selectMember(@PathVariable Long memberId) {

        Member member = memberService.selectMember(memberId);

        // 성공시 Custom 방식으로 -> Return
        return ApiResponse.<MemberResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원이 조회 되었습니다")
                .data(MemberResponseDto.toMemberDto(member))
                .build();
    }

    @PostMapping
    public ApiResponse<MemberResponseDto> insertMember(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {

        Member joinMember = memberService.insertMember(memberSignUpDto.toMemberEntity());

        log.info("Member Info : {} " + joinMember.toString());

        return ApiResponse.<MemberResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원 가입을 성공하셨습니다")
                .data(MemberResponseDto.toMemberDto(joinMember))
                .build();
    }

    /*@PostMapping
    public ApiResponse<> addFriend(){

    }*/


}

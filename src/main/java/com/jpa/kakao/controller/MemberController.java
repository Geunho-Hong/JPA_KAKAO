package com.jpa.kakao.controller;

import com.jpa.kakao.common.ApiResponse;
import com.jpa.kakao.common.StatusEnum;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.dto.member.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse> selectMember(@PathVariable Long memberId){

        ApiResponse<Member> member = ApiResponse.<Member>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원이 조회 되었습니다")
                .data(memberService.selectMember(memberId))
                .build();

        return ResponseEntity.ok(member);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> insertMember(@RequestBody MemberSignUpDto memberSignUpDto){

        Long memberNo = memberService.insertMember(memberSignUpDto.toMemberEntity());

        // 이런식으로 두 번 불러오면 문제가 발생?
        ApiResponse<Member> member = ApiResponse.<Member>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원 가입이 성공하셨습니다")
                .data(memberService.selectMember(memberNo))
                .build();

        return ResponseEntity.ok(member);

    }


}

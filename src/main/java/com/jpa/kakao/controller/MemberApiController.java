package com.jpa.kakao.controller;

import com.jpa.kakao.common.ApiResponse;
import com.jpa.kakao.common.StatusEnum;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.dto.friend.AddFriendResponseDto;
import com.jpa.kakao.dto.member.MemberValidResponseDto;
import com.jpa.kakao.dto.member.MemberResponseDto;
import com.jpa.kakao.dto.member.MemberSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/{memberNo}")
    public ApiResponse<MemberResponseDto> selectMember(@PathVariable Long memberNo) {

        Member member = memberService.selectMember(memberNo);

        return ApiResponse.<MemberResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원이 조회 되었습니다")
                .data(MemberResponseDto.toMemberDto(member))
                .build();
    }

    @PostMapping("")
    public ApiResponse<MemberResponseDto> insertMember(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {

        Member joinMember = memberService.insertMember(memberSignUpDto.toMemberEntity());

//        System.out.println("joinMember = " + joinMember);

        return ApiResponse.<MemberResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("회원 가입을 성공하셨습니다")
                .data(MemberResponseDto.toMemberDto(joinMember))
                .build();
    }

    @PostMapping("/friends/{memberId}/{friendId}")
    public ApiResponse<AddFriendResponseDto> addFriend(@PathVariable String memberId,
                                                       @PathVariable String friendId) {
        //TODO: api 관점에서는 memberNo 사용이 더 좋음
        Long relationShipNo = memberService.addFriend(memberId, friendId);

        return ApiResponse.<AddFriendResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message("친구 추가에 성공하셨습니다")
                .data(new AddFriendResponseDto(relationShipNo))
                .build();

    }

    @GetMapping("/exist/email/{email}")
    public ApiResponse<MemberValidResponseDto> existByEmail(@PathVariable String email) {
        //TODO: 이메일.com 관련해서 에러가 발생

        boolean isExistEmail = memberRepository.existsByEmail(email);

        return ApiResponse.<MemberValidResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(isExistEmail ? "이미 존재하는 이메일 입니다" : "사용 가능한 이메일 입니다")
                .data(new MemberValidResponseDto(isExistEmail))
                .build();
    }

    @GetMapping("/exist/memberId/{memberId}")
    public ApiResponse<MemberValidResponseDto> existMemberId(@PathVariable String memberId) {

        boolean isExistMemberId = memberRepository.existsByMemberId(memberId);

        return ApiResponse.<MemberValidResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(isExistMemberId ? "이미 존재하는 아이디 입니다" : "사용 가능한 아이디 입니다")
                .data(new MemberValidResponseDto(isExistMemberId))
                .build();
    }

    @GetMapping("/exist/phone/{phoneNumber}")
    public ApiResponse<MemberValidResponseDto> existPhoneNumber(@PathVariable String phoneNumber) {
        // DTO 형태로 Return 해줘야 Key - value 형태로 데이터를 받을 수 있다.
        boolean isExistPhoneNumber = memberRepository.existsByPhoneNumber(phoneNumber);

        return ApiResponse.<MemberValidResponseDto>builder()
                .status(StatusEnum.OK.getStatusCode())
                .message(isExistPhoneNumber ? "이미 존재하는 핸드폰 번호 입니다" : "사용 가능한 핸드폰 번호 입니다")
                .data(new MemberValidResponseDto(isExistPhoneNumber))
                .build();
    }

}

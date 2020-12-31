package com.jpa.kakao.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.kakao.common.StatusEnum;
import com.jpa.kakao.controller.MemberController;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.dto.member.MemberSignUpDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDate;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@WebMvcTest(MemberController.class)
public class MemberTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 조회 테스트")
    public void getMember() throws Exception {

        Member member = Member.builder()
                .memberNo(1L)
                .memberId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        given(memberService.selectMember(1L))
                .willReturn(member);

        ResultActions result = mockMvc.perform(get("/api/members/{memberNo}",1L)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$['data'].name", is("홍근호")))
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message",is("회원이 조회 되었습니다")))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 테스트")
    public void signUpMemberTest() throws Exception {

        MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
                .memberNo(1L)
                .memberId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        Member member = memberSignUpDto.toMemberEntity();

       // given(memberService.insertMember(member)).willReturn(member);

        ResultActions result = mockMvc.perform(post("/api/members")
                .content(objectMapper.writeValueAsString(memberSignUpDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)

        );

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                //.andExpect(jsonPath("$.message", is("회원 가입을 성공하셨습니다")))
                .andDo(print());

    }
}

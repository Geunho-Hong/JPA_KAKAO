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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
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
                .kakaoId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        given(memberService.selectMember(1L))
                .willReturn(member);


        ResultActions result = mockMvc.perform(get("/api/members")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("홍근호")))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 테스트")
    public void signUpMemberTest() throws Exception {

        MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
                .kakaoId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        ResultActions result = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSignUpDto))
                .accept(MediaType.APPLICATION_JSON)
        );

        result
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원 가입을 성공하셨습니다")))
                .andDo(print());

    }
}

package com.jpa.kakao.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.kakao.common.StatusEnum;
import com.jpa.kakao.controller.MemberApiController;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.dto.member.MemberSignUpDto;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@AutoConfigureMockMvc
@WebMvcTest(MemberApiController.class)
public class MemberApiTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;

    static EnhancedRandom memberRandomObject;

    @Test
    @DisplayName("DI 테스트")
    void dependencyTest() {
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(memberService).isNotNull();
        assertThat(memberRepository).isNotNull();
    }

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

        ResultActions result = mockMvc.perform(get("/api/members/{memberNo}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$['data'].name", is("홍근호")))
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원이 조회 되었습니다")))
                .andDo(print());
    }

    @Test
    @DisplayName("멤버 회원가입 테스트")
    public void signUpMemberTest() throws Exception {

        //TODO : TEST-CODE 에서 문제가 발생하고 있음. 어떤 문제인지 찾을 필요성이 있음
        // 이유를 봐도 잘 모르겠다 ....

        MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
                .memberId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        Member member = memberSignUpDto.toMemberEntity();

        Member joinMember = Member.builder()
                .memberNo(1L)
                .build();

        given(memberService.insertMember(memberSignUpDto.toMemberEntity())).willReturn(joinMember);

        mockMvc.perform(post("/api/members")
                .content(objectMapper.writeValueAsString(memberSignUpDto))
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원 가입을 성공하셨습니다")));

        /*ResultActions result = mockMvc.perform(post("/api/members")
                .content(objectMapper.writeValueAsString(memberSignUpDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        result
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원 가입을 성공하셨습니다")))
                .andDo(print());*/

    }

    @Test
    @DisplayName("회원 아이디 중복 검사 테스트")
    void isExistMemberId() throws Exception {

        String memberId = "grayson";

        given(memberRepository.existsByMemberId(memberId))
                .willReturn(true);

        ResultActions result = mockMvc.perform(get("/api/members/exist/memberId/{memberId}", memberId)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 아이디 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))
                .andDo(print());

    }

    @Test
    @DisplayName("회원 핸드폰 번호 중복 검사 테스트")
    void isExistPhoneNumber() throws Exception {

        String phoneNumber = "010-7122-8159";

        given(memberRepository.existsByPhoneNumber(phoneNumber))
                .willReturn(true);

        ResultActions result = mockMvc.perform(get("/api/members/exist/phone/{phoneNumber}", phoneNumber)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 핸드폰 번호 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 이메일 중복 검사 테스트")
    void isExistEmail() throws Exception {

        String email = "hogu8159@naver.com";

        given(memberRepository.existsByEmail(email))
                .willReturn(true);

        ResultActions result = mockMvc.perform(get("/api/members/exist/email/{email}", email)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 이메일 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))
                .andDo(print());
    }
}

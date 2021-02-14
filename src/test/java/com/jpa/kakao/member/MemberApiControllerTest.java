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
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static com.jpa.kakao.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.jpa.kakao.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INTEGER;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;

@Log4j2
@WebMvcTest(MemberApiController.class)
//@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;    // 서버에 배포하지 않고 Spring MVC 동작 재현 가능

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
                .memberId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        given(memberService.selectMember(1L)).willReturn(member);

        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/members/{memberNo}", 1L)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원이 조회 되었습니다")))
                .andDo(document("member/find-member",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberNo").description("회원 번호")
                        ),
                        responseFields(

                                fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.password").type(JsonFieldType.STRING).description("패스워드"),
                                fieldWithPath("data.phoneNumber").type(JsonFieldType.STRING).description("핸드폰번호"),
                                fieldWithPath("data.birthDate").type(JsonFieldType.STRING).description("생년월일")
                        )
                ));
    }

    @Test
    @DisplayName("멤버 회원가입 테스트")
    public void signUpMemberTest() throws Exception {

        MemberSignUpDto memberSignUpDto = MemberSignUpDto.builder()
                .memberId("grayson")
                .password("password")
                .email("grayson@naver.com")
                .name("홍근호")
                .phoneNumber("010-7122-8159")
                .birthDate(LocalDate.of(1994, 1, 14))
                .build();

        given(memberService.insertMember(memberSignUpDto.toMemberEntity())).willReturn(1L);

        ResultActions result = mockMvc.perform(post("/api/members")
                .content(objectMapper.writeValueAsString(memberSignUpDto))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        result
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("회원 가입을 성공하셨습니다")))
                .andDo(print());

    }

    @Test
    @DisplayName("회원 아이디 중복 검사 테스트")
    void isExistMemberId() throws Exception {

        String memberId = "grayson";

        given(memberRepository.existsByMemberId(memberId)).willReturn(true);

        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/members/exist/memberId/{memberId}", memberId)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 아이디 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))

                .andDo(document("member/exist-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("멤버 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").type(INTEGER).description("상태코드"),
                                fieldWithPath("message").type(STRING).description("메세지"),
                                fieldWithPath("data.exist").type(BOOLEAN).description("멤버 아이디 존재 여부")
                        )
                ));
    }

    @Test
    @DisplayName("회원 핸드폰 번호 중복 검사 테스트")
    void isExistPhoneNumber() throws Exception {

        String phoneNumber = "010-7122-8159";

        given(memberRepository.existsByPhoneNumber(phoneNumber))
                .willReturn(true);

        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/members/exist/phone/{phoneNumber}", phoneNumber)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 핸드폰 번호 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))
                .andDo(document("member/exist-phoneNumber",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("phoneNumber").description("멤버 핸드폰 번호")
                        ),
                        responseFields(
                                fieldWithPath("status").type(INTEGER).description("상태코드"),
                                fieldWithPath("message").type(STRING).description("메세지"),
                                fieldWithPath("data.exist").type(BOOLEAN).description("멤버 핸드폰 번호 존재 여부")
                        )
                ));
    }

    @Test
    @DisplayName("회원 이메일 중복 검사 테스트")
    void isExistEmail() throws Exception {

        String email = "hogu8159@naver.com";

        given(memberRepository.existsByEmail(email))
                .willReturn(true);

        ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/members/exist/email/{email}", email)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(StatusEnum.OK.getStatusCode())))
                .andExpect(jsonPath("$.message", is("이미 존재하는 이메일 입니다")))
                .andExpect(jsonPath("$.data.exist", is(true)))
                .andDo(document("member/exist-email",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("email").description("멤버 이메일")
                        ),
                        responseFields(
                                fieldWithPath("status").type(INTEGER).description("상태코드"),
                                fieldWithPath("message").type(STRING).description("메세지"),
                                fieldWithPath("data.exist").type(BOOLEAN).description("이메일 존재 여부")
                        )
                ));
    }
}

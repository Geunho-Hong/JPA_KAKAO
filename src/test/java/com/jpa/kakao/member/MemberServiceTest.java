package com.jpa.kakao.member;

import com.jpa.kakao.domain.friend.FriendRepository;
import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
import com.jpa.kakao.domain.member.MemberService;
import com.jpa.kakao.domain.member.exception.EmailDuplicateException;
import com.jpa.kakao.domain.member.exception.MemberIdDuplicateException;
import com.jpa.kakao.domain.member.exception.PhoneNumberDuplicateException;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // = junit4의 Run with 과 동일하다
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    FriendRepository friendRepository;

    static EnhancedRandom memberRandomObject;
    static EnhancedRandom friendRandomObject;


    @BeforeAll
    static void setUp(){
        memberRandomObject = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .stringLengthRange(3, 5)
                .dateRange(LocalDate.of(1920, 1, 1), LocalDate.of(2005, 1, 1))
                .excludeField(f -> f.getName().equals("memberNo"))
                .excludeField(f -> f.getName().equals("regDate"))
                .excludeField(f -> f.getName().equals("modifiedDate"))
                .randomize(f -> f.getName().equals("email"),() -> "test@naver.com")
                .build();

        friendRandomObject = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .stringLengthRange(3,5)
                .dateRange(LocalDate.of(1920,1,1),LocalDate.of(2005,1,1))
                .excludeField(f -> f.getName().equals("memberNo"))
                .excludeField(f -> f.getName().equals("regDate"))
                .excludeField(f -> f.getName().equals("modifiedDate"))
                .build();
    }

    @Test
    @DisplayName("회원 가입")
    void insertMemberTest(){

        Member member = memberRandomObject.nextObject(Member.class);

        given(memberRepository.existsByEmail(member.getEmail())).willReturn(false);
        given(memberRepository.existsByMemberId(member.getMemberId())).willReturn(false);
        given(memberRepository.existsByPhoneNumber(member.getPhoneNumber())).willReturn(false);

        memberService.insertMember(member);


    }

    @Test
    @DisplayName("친구 추가 기능")
    void addFriendTest(){

        Member member = memberRandomObject.nextObject(Member.class);
        Member friend = friendRandomObject.nextObject(Member.class);

        given(memberRepository.findByMemberId(member.getMemberId()))
                .willReturn(Optional.of(member));

        given(memberRepository.findByMemberId(friend.getMemberId()))
                .willReturn(Optional.of(friend));

    }

    @Test
    @DisplayName("이메일 중복 검사")
    void existByEmail(){

        Member member = memberRandomObject.nextObject(Member.class);

        given(memberRepository.existsByEmail(member.getEmail())).willReturn(true);

        // then
        assertThatThrownBy(() -> memberService.insertMember(member))
                .isInstanceOf(EmailDuplicateException.class);

    }

    @Test
    @DisplayName("계정 아이디 존재 여부 확인")
    void existByMemberId(){

        Member member = memberRandomObject.nextObject(Member.class);

        given(memberRepository.existsByMemberId(member.getMemberId())).willReturn(true);

        // then
        assertThatThrownBy(() -> memberService.insertMember(member))
                .isInstanceOf(MemberIdDuplicateException.class);

    }

    @Test
    @DisplayName("핸드폰 번호 검사")
    void existByPhoneNumber(){

        Member member = memberRandomObject.nextObject(Member.class);

        given(memberRepository.existsByPhoneNumber(member.getPhoneNumber())).willReturn(true);

        // then
        assertThatThrownBy(() -> memberService.insertMember(member))
                .isInstanceOf(PhoneNumberDuplicateException.class);

    }




}

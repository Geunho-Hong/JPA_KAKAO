package com.jpa.kakao.member;

import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    MemberRepository memberRepository;

    static EnhancedRandom memberCreator;

    @BeforeAll
    static void setup() {
        memberCreator = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .stringLengthRange(3, 5)
                .dateRange(LocalDate.of(1920, 1, 1), LocalDate.of(2005, 1, 1))
                .excludeField(f -> f.getName().equals("memberNo"))
                .randomize(f -> f.getName().equals("email"),() -> "test@naver.com")
                .build();
    }

    @Test
    @DisplayName("DI 테스트")
    void dependencyTest(){
        assertThat(memberRepository).isNotNull();
        assertThat(testEntityManager).isNotNull();
    }

    @Test
    @DisplayName("정상적인 회원 저장 성공")
    void save() {

        // given
      /*  Member member = Member.builder()
                .memberId("test")
                .password("8159")
                .email("test@naver.com")
                .name("홍근호")
                .phoneNumber("010-1111-8159")
                .birthDate(LocalDate.of(1994,1,14))
                .build();*/

        Member member =memberCreator.nextObject(Member.class);

        // when
        memberRepository.save(member);
        testEntityManager.flush();

        Member findMember
                = memberRepository.findById(member.getMemberNo()).orElseThrow(EntityNotFoundException::new);

        // then
        assertThat(findMember).isEqualTo(member).isSameAs(member);
        assertThat(findMember.getMemberNo()).isNotZero();
        assertThat(findMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember.getBirthDate()).isEqualTo(member.getBirthDate());

    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    void existByEmailTest(){

        Member member = memberCreator.nextObject(Member.class);

        memberRepository.save(member);

        boolean isExistEmail = memberRepository.existsByEmail(member.getEmail());

        assertThat(isExistEmail).isTrue();

    }

    @Test
    @DisplayName("회원 아이디 존재 여부 확인")
    void existByMemberIdTest(){

        Member member = memberCreator.nextObject(Member.class);

        memberRepository.save(member);

        boolean isExistMemberId = memberRepository.existsByMemberId(member.getMemberId());

        assertThat(isExistMemberId).isTrue();
    }

    @Test
    @DisplayName("핸드폰 번호 존재 여부 확인")
    void existPhoneNumberTest(){

        Member member = memberCreator.nextObject(Member.class);

        memberRepository.save(member);

        boolean isExistPhoneNumber = memberRepository.existsByPhoneNumber(member.getPhoneNumber());

        assertThat(isExistPhoneNumber).isTrue();

    }

    @Test
    @DisplayName("회원 아이디로 멤버 조회")
    void findByMemberIdTest(){

        Member member = memberCreator.nextObject(Member.class);

        memberRepository.save(member);

        Optional<Member> findMember =
                Optional.ofNullable(memberRepository.findByMemberId(member.getMemberId())
                        .orElseThrow(EntityNotFoundException::new));

        assertThat(findMember.orElse(null).getMemberId()).isEqualTo(member.getMemberId());
        assertThat(findMember.orElse(null).getEmail()).isEqualTo(member.getEmail());
        assertThat(findMember.orElse(null).getPassword()).isEqualTo(member.getPassword());
        assertThat(findMember.orElse(null).getName()).isEqualTo(member.getName());
        assertThat(findMember.orElse(null).getBirthDate()).isEqualTo(member.getBirthDate());

    }
}

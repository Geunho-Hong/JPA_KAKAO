package com.jpa.kakao.member;

import com.jpa.kakao.domain.member.Member;
import com.jpa.kakao.domain.member.MemberRepository;
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
import static io.github.benas.randombeans.EnhancedRandomBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    MemberRepository memberRepository;

    static EnhancedRandom memberCreator;

    @BeforeAll
    static void setup() {
        memberCreator = aNewEnhancedRandomBuilder()
                .stringLengthRange(3, 5)
                .dateRange(LocalDate.of(1920, 1, 1), LocalDate.of(2005, 1, 1))
                .build();
    }

    @Test
    @DisplayName("정상적인 회원 저장 성공")
    void save() {

        // given
        Member member = memberCreator.nextObject(Member.class);

        System.out.println("member : {} " + member.toString());

        // when
        memberRepository.save(member);

        em.flush();

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
}

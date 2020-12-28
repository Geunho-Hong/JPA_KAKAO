package com.jpa.kakao.domain.member;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.domain.member.exception.EmailDuplicateException;
import com.jpa.kakao.domain.member.exception.KakaoIdDuplicateException;
import com.jpa.kakao.domain.member.exception.MemberNotFoundException;
import com.jpa.kakao.domain.member.exception.PhoneNumberDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member insertMember(Member member){
        validSignUpMember(member);
        return memberRepository.save(member);
    }

    public Member selectMember(Long memberNo){
        return memberRepository
                .findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException(memberNo));
    }

    public void validSignUpMember(Member member){
        validMemberEmail(member.getEmail());
        validKakaoId(member.getKakaoId());
        validPhoneNumber(member.getPhoneNumber());
    }

    private void validMemberEmail(String email){
        if(memberRepository.existsByEmail(email)){
            throw new EmailDuplicateException(ErrorCode.EMAIL_DUPLICATION.getMessage());
        }
    }

    private void validKakaoId(String kakaoId){
        if(memberRepository.existsByKakaoId(kakaoId)){
            throw new KakaoIdDuplicateException(ErrorCode.KAKAO_DUPLICATION.getMessage());
        }
    }

    private void validPhoneNumber(String phoneNumber){
        if(memberRepository.existsByPhoneNumber(phoneNumber)){
            throw new PhoneNumberDuplicateException(ErrorCode.PHONE_NUMBER_DUPLICATION.getMessage());
        }
    }


}

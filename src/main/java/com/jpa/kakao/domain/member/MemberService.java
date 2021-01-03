package com.jpa.kakao.domain.member;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.common.error.EntityNotFoundException;
import com.jpa.kakao.domain.FriendRelationShip;
import com.jpa.kakao.domain.friend.FriendRepository;
import com.jpa.kakao.domain.member.exception.EmailDuplicateException;
import com.jpa.kakao.domain.member.exception.MemberIdDuplicateException;
import com.jpa.kakao.domain.member.exception.MemberNotFoundException;
import com.jpa.kakao.domain.member.exception.PhoneNumberDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public Member insertMember(Member member) {
        validSignUpMember(member);
        return memberRepository.save(member);
    }

    public Member selectMember(Long memberNo) {
        return memberRepository
                .findById(memberNo)
                .orElseThrow(() -> new MemberNotFoundException(memberNo));
    }

    public Long addFriend(String memberId, String friendId) {

        if (memberId.equals(friendId)) {
            throw new IllegalArgumentException("자기 자신은 친구 추가할 수 없습니다");
        }

        Optional<Member> member = Optional.ofNullable(memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다")));

        Optional<Member> friend = Optional.ofNullable(memberRepository.findByMemberId(friendId)
                .orElseThrow(() -> new EntityNotFoundException("친구를 찾을 수 없습니다")));

        FriendRelationShip friendRelationShip = FriendRelationShip.addFriend(member.orElse(null), friend.orElse(null));
        friendRepository.save(friendRelationShip);

        return friendRelationShip.getRelationShipNo();
    }

    public void validSignUpMember(Member member) {
        validMemberEmail(member.getEmail());
        validmemberId(member.getMemberId());
        validPhoneNumber(member.getPhoneNumber());
    }

    private void validMemberEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailDuplicateException(ErrorCode.EMAIL_DUPLICATION.getMessage());
        }
    }

    private void validmemberId(String memberId) {
        if (memberRepository.existsByMemberId(memberId)) {
            throw new MemberIdDuplicateException(ErrorCode.ID_DUPLICATION.getMessage());
        }
    }

    private void validPhoneNumber(String phoneNumber) {
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberDuplicateException(ErrorCode.PHONE_NUMBER_DUPLICATION.getMessage());
        }
    }


}

package com.jpa.kakao.domain.member.exception;

import com.jpa.kakao.common.error.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(Long memberNo){
        super("memberNo : " + memberNo + " is not exist");
    }
}

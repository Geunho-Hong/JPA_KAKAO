package com.jpa.kakao.domain.member.exception;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.common.error.BusinessException;

public class MemberIdDuplicateException extends BusinessException {

    public MemberIdDuplicateException(String message){
        super(message, ErrorCode.ID_DUPLICATION);
    }

}

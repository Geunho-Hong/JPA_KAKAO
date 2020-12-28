package com.jpa.kakao.domain.member.exception;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.common.error.BusinessException;

public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException(String message){
        super(message, ErrorCode.EMAIL_DUPLICATION);
    }

}

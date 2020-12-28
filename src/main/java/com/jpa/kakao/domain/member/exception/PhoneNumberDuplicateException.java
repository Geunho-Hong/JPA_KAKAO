package com.jpa.kakao.domain.member.exception;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.common.error.BusinessException;

public class PhoneNumberDuplicateException extends BusinessException {

    public PhoneNumberDuplicateException(String message){
        super(message, ErrorCode.PHONE_NUMBER_DUPLICATION);
    }

}

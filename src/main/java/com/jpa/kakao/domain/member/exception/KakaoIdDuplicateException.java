package com.jpa.kakao.domain.member.exception;

import com.jpa.kakao.common.ErrorCode;
import com.jpa.kakao.common.error.BusinessException;

public class KakaoIdDuplicateException extends BusinessException {

    public KakaoIdDuplicateException(String message){
        super(message, ErrorCode.KAKAO_DUPLICATION);
    }

}

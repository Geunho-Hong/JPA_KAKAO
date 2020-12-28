package com.jpa.kakao.common.error;

import com.jpa.kakao.common.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message){
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }

}

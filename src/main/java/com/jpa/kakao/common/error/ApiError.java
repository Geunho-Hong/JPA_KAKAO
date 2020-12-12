package com.jpa.kakao.common.error;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiError {

    private LocalDateTime timeStamp;

    private Integer status;

    private String message;

    private List<FieldError> errors;

    private String code;


}

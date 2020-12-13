package com.jpa.kakao.common.error;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiError {

    private LocalDateTime timeStamp;

    private Integer status;

    private String message;

    //private List<FieldError> errors;

    private String code;


}

package com.jpa.kakao.common;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private Integer status;

    private String message;

    private T data;

}

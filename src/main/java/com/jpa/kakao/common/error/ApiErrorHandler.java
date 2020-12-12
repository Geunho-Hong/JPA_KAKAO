package com.jpa.kakao.common.error;

import com.jpa.kakao.common.ApiResponse;
import com.jpa.kakao.common.ErrorCodeEnum;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Log4j
@RestControllerAdvice
public class ApiErrorHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleException(HttpServletRequest request, Exception e){

       ApiError apiError =  ApiError.builder()
                .message(ErrorCodeEnum.SERVER_ERROR.getMessage())
                .timeStamp(LocalDateTime.now())
                .code(ErrorCodeEnum.SERVER_ERROR.getCode())
                .build();

        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }


}

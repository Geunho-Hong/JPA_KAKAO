package com.jpa.kakao.common.error;

import com.jpa.kakao.common.ErrorCodeEnum;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ApiErrorHandler {

    // @Valid 유효성 검사 진행 중 오류가 발생할시 진행
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValildException Handler: {}", e.getMessage());
        ApiError apiError = ApiError.builder()
                .message("Input is not valid")
                .timeStamp(LocalDateTime.now())
                .code("400")
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // 찾아온 Entity가 없을때 진행
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
        log.error("EntityNotFoundException : {} ", e.getMessage());
        ApiError apiError = ApiError.builder()
                .message("Entity is not Found")
                .timeStamp(LocalDateTime.now())
                .code("404")
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiError> handleException(HttpServletRequest request, Exception e) {
        log.error("Server Error Occur : {} ", e.getMessage());
        ApiError apiError = ApiError.builder()
                .message(ErrorCodeEnum.SERVER_ERROR.getMessage())
                .timeStamp(LocalDateTime.now())
                .code(ErrorCodeEnum.SERVER_ERROR.getCode())
                .build();

        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }


}

package com.korit.board.controller;

import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 나머지 오류들은 advice에서 처리하겠다.
@ControllerAdvice
public class ExceptionControllerAdvice {

    @TimeAop
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException validException) {
        System.out.println("예외처리됨 !!");
        return ResponseEntity.badRequest().body(validException.getErrorMap());
    }
}

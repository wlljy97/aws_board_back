package com.korit.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.exception.AuthMailException;
import com.korit.board.exception.DuplicateException;
import com.korit.board.exception.MismatchedPasswordException;
import com.korit.board.exception.ValidException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// 나머지 오류들은 advice에서 처리하겠다.
@RestControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> validException(ValidException validException) {
        return ResponseEntity.badRequest().body(validException.getErrorMap());
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> duplicateException(DuplicateException duplicateException) {
        return ResponseEntity.badRequest().body(duplicateException.getErrorMap());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException) {
        Map<String, String> message = new HashMap<>();
        message.put("authError", "사용자 정보를 확인 해주세요."); // 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsException(BadCredentialsException badCredentialsException) {
        Map<String, String> message = new HashMap<>();
        message.put("authError", "사용자 정보를 확인 해주세요."); // 401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> disabledException(DisabledException disabledException) {
        Map<String, String> message = new HashMap<>();
        message.put("Disabled", "이메일 인증 필요 합니다."); //403
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> jwtException(JwtException jwtException) {
        Map<String, String> message = new HashMap<>();
        message.put("jwt", "인증이 유효하지 않습니다.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(AuthMailException .class)
    public ResponseEntity<?> MailException(AuthMailException MailException) {
        Map<String, String> message = new HashMap<>();
        message.put("authMail", MailException.getMessage()); // 생성해놓은 메세지가 응답할것임
        return ResponseEntity.ok().body(message); // ok를 안주면 오류페이지를 나타냄
    }

    @ExceptionHandler(MismatchedPasswordException.class)
    public ResponseEntity<?> mismatchedPasswordException(MismatchedPasswordException mismatchedPasswordException) {
        Map<String, String> message = new HashMap<>();
        message.put("mismatched", mismatchedPasswordException.getMessage());
        return ResponseEntity.badRequest().body(message); // badRequest 요청 자체가 잘못된것
    }

}

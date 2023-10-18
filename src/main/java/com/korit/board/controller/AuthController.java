package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ReturnAop;
import com.korit.board.aop.annotation.TimeAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.SignupReqDto;
import com.korit.board.exception.ValidException;
import com.korit.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ReturnAop
    @ArgsAop
    @TimeAop
    @ValidAop

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(
            @Valid @RequestBody SignupReqDto signupReqDto, // HTTP 요청의 본문(JSON)을 SignupReqDto 객체로 역직렬화하고, @Valid 어노테이션을 사용하여 유효성 검사를 수행
            BindingResult bindingResult) { // <- 요청 데이터

        // @Valid 어노테이션을 쓰면 BindingResult bindingResult 도 무조건 함께 써야한다.

        System.out.println("AuthController!!!");

        return ResponseEntity.ok(authService.signup(signupReqDto));
    }

}

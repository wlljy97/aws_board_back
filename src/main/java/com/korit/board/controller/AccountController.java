package com.korit.board.controller;

import com.korit.board.dto.PrincipalReqDto;
import com.korit.board.entity.User;
import com.korit.board.security.PrincipalUser;
import com.korit.board.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final MailService mailService;

    // 인증이 되면 controller로 온다.
    @GetMapping("/account/principal")
    public ResponseEntity<?> getPrincipal() {
        PrincipalUser principalUser =
                (PrincipalUser) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal(); // getPrincipal이 PrincipalUser를 가진다.
        User user = principalUser.getUser();
        PrincipalReqDto principalReqDto = user.principalReqDto();

        return ResponseEntity.ok(principalReqDto);
    }

    @PostMapping("/account/mail/auth")
    public ResponseEntity<?> sendAuthenticationMail() {

        return ResponseEntity.ok(mailService.sendMail());
    }
}

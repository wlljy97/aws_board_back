package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.PrincipalRespDto;
import com.korit.board.dto.UpdatePasswordReqDto;
import com.korit.board.dto.UpdateProfileImgReqDto;
import com.korit.board.entity.User;
import com.korit.board.security.PrincipalUser;
import com.korit.board.service.AccountService;
import com.korit.board.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final MailService mailService;

    // 인증이 되면 controller로 온다.
    @GetMapping("/account/principal")
    public ResponseEntity<?> getPrincipal() {
        PrincipalUser principalUser =
                (PrincipalUser) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal(); // getPrincipal이 PrincipalUser를 가진다.
        User user = principalUser.getUser();
        PrincipalRespDto principalReqDto = user.principalReqDto();

        return ResponseEntity.ok(principalReqDto);
    }

    @PostMapping("/account/mail/auth")
    public ResponseEntity<?> sendAuthenticationMail() {

        return ResponseEntity.ok(mailService.sendMail());
    }


    @PutMapping("/account/profile/img")
    public  ResponseEntity<?> updateProfileImg(@RequestBody UpdateProfileImgReqDto updateProfileImgReqDto) {

        return ResponseEntity.ok(accountService.updateProfileImg(updateProfileImgReqDto));
    }

    @ArgsAop
    @ValidAop
    @PutMapping("/account/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordReqDto updatePasswordReqDto,
                                            BindingResult bindingResult) {

        return ResponseEntity.ok(accountService.updatePassword(updatePasswordReqDto));
    }
}

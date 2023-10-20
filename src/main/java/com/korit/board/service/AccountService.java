package com.korit.board.service;

import com.korit.board.dto.UpdatePasswordReqDto;
import com.korit.board.dto.UpdateProfileImgReqDto;
import com.korit.board.entity.User;
import com.korit.board.exception.AuthMailException;
import com.korit.board.exception.MismatchedPasswordException;
import com.korit.board.jwt.JwtProvider;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserMapper userMapper; // usermapper를 통해 이메일 인증 여부 확인
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class) // update가 일어나면 달아주는 어노테이션
    public boolean authenticateMail(String token) {
        Claims claims = jwtProvider.getClaims(token);
        if (claims == null) { // null 값이면 token 사용 불가능
            throw new AuthMailException("만료된 인증 요청입니다."); // token 만료 됬거나 위조 됬으면 뜨게함 // 생성자에게 매개변수를 던져줌
        }

        String email = claims.get("email").toString(); // 인증 요청할 user의 enabled를 인증함.
        System.out.println(email);
        User user = userMapper.findUserByEmail(email);
        if(user.getEnabled() > 0){
            throw new AuthMailException("이미 인증이 완료된 요청입니다.");
        }

        return  userMapper.updateEnabledToEmail(email) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateProfileImg(UpdateProfileImgReqDto updateProfileImgReqDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userMapper.updateProfileUrl(User.builder()
                .email(email)
                .profileUrl(updateProfileImgReqDto.getProfileUrl())
                .build()) > 0;
    }

    public boolean updatePassword(UpdatePasswordReqDto updatePasswordReqDto) {
        PrincipalUser principalUser = (PrincipalUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principalUser.getUser();

        // 이전 비밀번호 일치
        if(!passwordEncoder.matches(updatePasswordReqDto.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("BadCredentials");
        }

        // 새로운 비밀번호 일치
        if(!Objects.equals(updatePasswordReqDto.getNewPassword(), updatePasswordReqDto.getCheckNewPassword())) {
            throw new MismatchedPasswordException();
        }

        user.setPassword(passwordEncoder.encode(updatePasswordReqDto.getNewPassword())); // 비밀번호를 암호화 해서 user에게 넘겨준다.

        return userMapper.updatePassword(user) > 0;
    }
}

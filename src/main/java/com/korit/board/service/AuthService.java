package com.korit.board.service;

import com.korit.board.dto.MergeOauth2ReqDto;
import com.korit.board.dto.SigninReqDto;
import com.korit.board.dto.SignupReqDto;
import com.korit.board.entity.User;
import com.korit.board.exception.DuplicateException;
import com.korit.board.exception.MismatchedPasswordException;
import com.korit.board.jwt.JwtProvider;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PrincipalProvider principalProvider; // 직접 provider를 만든다.
    private final JwtProvider jwtProvider;

    public boolean signup(SignupReqDto signupReqDto) {
        User user = signupReqDto.toUserEntity(passwordEncoder);

        int errorCode = userMapper.checkDuplicate(user); //  사용자의 이메일과 닉네임 중복 검사를 수행
        if(errorCode > 0) {
            responseDuplicateError(errorCode); //중복 오류 응답을 생성
        }

        return userMapper.saveUser(user) > 0; // 중복이 없는 경우 userMapper.saveUser(user)를 호출하여 사용자를 데이터베이스에 저장
    }

    private void responseDuplicateError(int errorCode) { // 중복 에러 처리
        Map<String, String> errorMap = new HashMap<>();
        switch (errorCode) {
            case 1:
                errorMap.put("email", "이미 사용중인 이메일입니다.");
                break;
            case 2:
                errorMap.put("nickname", "이미 사용중인 닉네임입니다.");
                break;
            case 3:
                errorMap.put("email", "이미 사용중인 이메일입니다.");
                errorMap.put("nickname", "이미 사용중인 닉네임입니다.");
                break;
        }
        throw new DuplicateException(errorMap);
        // 이 예외가 일어나면 duplicate의 중복 오류 검사
        // ExceptionControllerAdvice의 duplicate 에서 검사 실행
    }

    public String signin(SigninReqDto signinReqDto) {
        UsernamePasswordAuthenticationToken authenticationToken = // authentication 업캐스팅이 가능하다.
                new UsernamePasswordAuthenticationToken(signinReqDto.getEmail(), signinReqDto.getPassword());

        Authentication authentication = principalProvider.authenticate(authenticationToken); // principalProvider.authenticate 메소드를 사용하여 인증 수행
        // 성공적으로 인증된 경우, jwtProvider.generateToken을 사용하여 JWT 토큰을 생성하고 반환
        return jwtProvider.generateToken(authentication);
    }

    public boolean authenticate(String token) { // authenticate 메서드는 JWT 토큰을 검증하여 사용자 인증을 확인
        Claims claims = jwtProvider.getClaims(token);
        if(claims == null) {
            throw new JwtException("인증 토큰 유효성 검사 실패");
        }
        return Boolean.parseBoolean(claims.get("enabled").toString());
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean mergeOauth2(MergeOauth2ReqDto mergeOauth2ReqDto) {
        User user = userMapper.findUserByEmail(mergeOauth2ReqDto.getEmail()); // 이 단계 까지 오면 email이 인증 되었다는 것

        if(!passwordEncoder.matches(mergeOauth2ReqDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("BadCredentials");
        }

        return userMapper.updateOauth2IdAndProvider(mergeOauth2ReqDto.toUserEntity()) > 0;
    }
}


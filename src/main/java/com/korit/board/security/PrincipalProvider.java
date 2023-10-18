package com.korit.board.security;

import com.korit.board.service.PrincipalUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrincipalProvider implements AuthenticationProvider {

    private final PrincipalUserDetailsService principalUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName(); // 여기서 getName 이 email
        String password = (String) authentication.getCredentials(); //  여기서 getCredentials 이 password

        UserDetails principalUser = principalUserDetailsService.loadUserByUsername(email); // UserDetails로 반환

        if(!passwordEncoder.matches(password, principalUser.getPassword())) { // 일치하지 않으면 예외
            throw new BadCredentialsException("BadCredentials"); // 비밀번호 틀림
        }

        return new UsernamePasswordAuthenticationToken(principalUser, password, principalUser.getAuthorities()); // password여기서는 암호화 되지 않은
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

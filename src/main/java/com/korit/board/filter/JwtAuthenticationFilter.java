package com.korit.board.filter;

import com.korit.board.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String bearerToken = httpServletRequest.getHeader("Authorization");
        String token = jwtProvider.getToken(bearerToken);
        Authentication authentication = jwtProvider.getAuthentication(token);

        // SecurityContextHolder 의 객체안에 getContext객체가 있고 setAuthentication 여기 안에 뭐든 들어가 있으면 뭐든 ok
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}

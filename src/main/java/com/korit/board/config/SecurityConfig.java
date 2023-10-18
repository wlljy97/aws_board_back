package com.korit.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 기존의 시큐리티를 쓰지않고 재정의한 시큐리티를 쓰겠다.
@Configuration // IoC에 객체로서 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 외부라이브러리 객체를 IoC에 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // HttpSecurity http 핵심 객체
//        super.configure(http); // 부모가 가지고 있는 원래의 설정, 이것을 쓰면 기존의 http를 쓰는 것
        http.cors(); // WebMvcConfig의 CORS 설정을 적용, cors의 정책을 따라가게 함
        http.csrf().disable(); // 서버사이드렌더링에서 쓴다. 요청과 응답에 대한 csrf토큰이 들어가 있어야한다.

        http.authorizeRequests() // 요청들의 인증을 거칠수 있게끔 하는 객체
                .antMatchers("/auth/**")
                .permitAll(); // "/auth/**" 이주소(/auth)로 시작하는(오는)것을 무조건 다 통과 시킴
    }
}

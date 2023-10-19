package com.korit.board.jwt;

import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final UserMapper userMapper;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); // 암호화 시킴
        this.userMapper = userMapper;
    }

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();

        Date date = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));


        // Jwt 토큰을 return / 토큰을 만듬
        return Jwts.builder()
                .setSubject("AccessToken")
                .setExpiration(date) // 토큰의 만료기간
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println(e.getClass() + e.getMessage());
        }
        return claims;
    }

    public String getToken(String bearerToken) {
        if(!StringUtils.hasText(bearerToken)) {
            return null;
        }
        return bearerToken.substring("Bearer ".length());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token); // null이면 토큰이 유효하지 않다.
        if(claims == null) {
            return null;
        }

        User user = userMapper.findUserByEmail(claims.get("email").toString());
        if(user == null) {
            return null;
        }

        PrincipalUser principalUser = new PrincipalUser(user);
        return new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
    }

    public String generateAuthMailToken(String email) {
        Date date = new Date(new Date().getTime() + 1000 * 60 * 5); // 인증을 할 수 있는데 까지의 시간은 5분

        return Jwts.builder()
                .setSubject("AuthenticationEmailToken")
                .setExpiration(date)
                .claim("email", email)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}

package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Data
public class SignupReqDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String nickname;

    public User toUser(BCryptPasswordEncoder passwordEncoder) { // 암호화를 위해서 객체를 생성

        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password)) // 암호화를 하기위해서 encode를 써줌
                .name(name)
                .nickname(nickname)
                .build();
    }

}

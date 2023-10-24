package com.korit.board.dto;

import com.korit.board.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MergeOauth2ReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String oauth2Id;
    @NotBlank
    private String provider;

    public User toUserEntity() {
        return User.builder()
                .email(email)
                .oauth2Id(oauth2Id) // 여기의 이메일을 찾아서 update를 해줘야 한다.
                .provider(provider)
                .build();
    }
}

package com.korit.board.entity;

import com.korit.board.dto.PrincipalReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 필수 어노테이션
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private int enabled;

    public PrincipalReqDto principalReqDto() {
        return PrincipalReqDto.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .nickname(nickname)
                .enabled(enabled > 0)
                .build();
    }
}

// principal user에 true,false로 들어감

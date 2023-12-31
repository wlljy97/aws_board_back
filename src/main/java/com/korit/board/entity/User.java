package com.korit.board.entity;

import com.korit.board.dto.PrincipalRespDto;
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
    private String profileUrl;
    private String oauth2Id;
    private String provider;
    private int userPoint;


    public PrincipalRespDto principalReqDto() {
        return PrincipalRespDto.builder()
                .userId(userId)
                .email(email)
                .name(name)
                .nickname(nickname)
                .enabled(enabled > 0)
                .profileUrl(profileUrl)
                .oauth2Id(oauth2Id)
                .provider(provider)
                .userPoint(userPoint)
                .build();
    }
}

// principal user에 true,false로 들어감

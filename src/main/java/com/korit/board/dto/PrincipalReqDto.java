package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrincipalReqDto { // 마이페이지에 필요한 것들
    private int userId;
    private String email;
    private String name;
    private String nickname;
    private boolean enabled;
    private String profileUrl;
    private String oauth2Id;
    private String provider;
}

package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrincipalReqDto {
    private int userId;
    private String email;
    private String name;
    private String nickname;
    private boolean enabled;
}

package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardListReqDto {
    private int boardId;
    private String title;
    private String nickname;
    private String createDate;
    private int hitsCount;
    private int likeCount;
}

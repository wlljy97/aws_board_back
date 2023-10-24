package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardCategoryReqDto {
    private int boardCategoryId;
    private String boardCategoryName;
}

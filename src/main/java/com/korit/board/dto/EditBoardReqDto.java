package com.korit.board.dto;

import com.korit.board.entity.Board;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class EditBoardReqDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Min(0) // 최소값이 0, 0보다 작은것들이 들어올 수 없다.
    private int categoryId; //  category가있으면 board만 insert category가 없으면 category insert,board insert

    @NotBlank
    private String categoryName;

    public Board toBoardEntity(String email) {
        return Board.builder()
                .boardTitle(title)
                .boardCategoryId(categoryId)
                .boardContent(content)
                .email(email)
                .build();
    }
}

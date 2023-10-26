package com.korit.board.entity;

import com.korit.board.dto.BoardListReqDto;
import com.korit.board.dto.GetBoardRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
    private int boardId;
    private String boardTitle;
    private int boardCategoryId;
    private String boardContent;
    private String email;
    private String nickname;
    private LocalDateTime createDate;
    private int boardHitsCount;
    private int boardLikeCount;

    public BoardListReqDto boardListReqDto() {
        return BoardListReqDto.builder()
                .boardId(boardId)
                .title(boardTitle)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ISO_DATE)) // 년/월/일 표기
                .hitsCount(boardHitsCount)
                .likeCount(boardLikeCount)
                .build();
    }

    public GetBoardRespDto toBoardDto() {
        return GetBoardRespDto.builder()
                .boardId(boardId)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardCategoryId(boardCategoryId)
                .email(email)
                .nickname(nickname)
                .createDate(createDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))) // ofLocalizedDate 나라별날짜 표기법으로 바꿔줌 LONG을 쓰면 요일 생략, FULL은 요일 나타냄
                .boardHitsCount(boardHitsCount)
                .boardLikeCount(getBoardLikeCount())
                .build();
    }
}

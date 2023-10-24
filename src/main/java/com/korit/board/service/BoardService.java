package com.korit.board.service;

import com.korit.board.dto.BoardCategoryReqDto;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardCategoryReqDto> getBoardCategoriesAll() {
        List<BoardCategoryReqDto> boardCategoryReqDtos = new ArrayList<>();
        boardMapper.getBoardCategories().forEach(category -> {
            boardCategoryReqDtos.add(category.toCategoryDto());
        });
        return boardCategoryReqDtos;
    }
}

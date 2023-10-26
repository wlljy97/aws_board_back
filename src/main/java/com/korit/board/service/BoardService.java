package com.korit.board.service;

import com.korit.board.dto.*;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional(rollbackFor = Exception.class)
    public boolean writeBoardContent(WriteBoardReqDto writeBoardReqDto) {
        BoardCategory boardCategory = null;
        if(writeBoardReqDto.getCategoryId() == 0) {
            boardCategory = BoardCategory.builder()
                    .boardCategoryName(writeBoardReqDto.getCategoryName())
                    .build();
            boardMapper.saveCategory(boardCategory); // 여기까지 되면 saveCategory insert가 된다.
            writeBoardReqDto.setCategoryId(boardCategory.getBoardCategoryId());
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Board board = writeBoardReqDto.toBoardEntity(email);
        return boardMapper.saveBoard(board) > 0;
    }

    public List<BoardListReqDto> getBoardList(String categoryName, int page, SearchBoardListReqDto searchBoardListReqDto) {
        int index = (page - 1) * 10;
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("index", index);
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        List<BoardListReqDto> boardListReqDtos = new ArrayList<>();
        boardMapper.getBoardList(paramsMap).forEach(board -> {
            boardListReqDtos.add(board.boardListReqDto());
        });
        return boardListReqDtos;
    }

    public int getBoardCount(String categoryName, SearchBoardListReqDto searchBoardListReqDto) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        return boardMapper.getBoardCount(paramsMap);
    }

    public GetBoardRespDto getBoard(int boardId) {
        return boardMapper.getBoardByBoardId(boardId).toBoardDto();
    }

    public boolean getLikeState(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.getLikeState(paramsMap) > 0;
    }

    public boolean setLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.insertLike(paramsMap) > 0;
    }

    public boolean cancelLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.deleteLike(paramsMap) > 0;
    }
}

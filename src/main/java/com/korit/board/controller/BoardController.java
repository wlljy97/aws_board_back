package com.korit.board.controller;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.EditBoardReqDto;
import com.korit.board.dto.RegisterBoardReqDto;
import com.korit.board.dto.SearchBoardListReqDto;
import com.korit.board.dto.WriteBoardReqDto;
import com.korit.board.exception.ValidException;
import com.korit.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/categories")
    public ResponseEntity<?> getCategories() {

        return ResponseEntity.ok(boardService.getBoardCategoriesAll());
    }

    @ArgsAop
    @ValidAop
    @PostMapping("/board/content")
    public ResponseEntity<?> writeBoard(@Valid @RequestBody WriteBoardReqDto writeBoardReqDto, BindingResult bindingResult) {

        return ResponseEntity.ok(boardService.writeBoardContent(writeBoardReqDto));
    }

    @ArgsAop
    @GetMapping("/boards/{categoryName}/{page}")
    public ResponseEntity<?> getBoardList(
            @PathVariable String categoryName,
            @PathVariable int page,
            SearchBoardListReqDto searchBoardListReqDto) {

        return ResponseEntity.ok(boardService.getBoardList(categoryName, page, searchBoardListReqDto));
    }

    @GetMapping("/boards/{categoryName}/count")
    public ResponseEntity<?> getBoardCount(
            @PathVariable String categoryName,
            SearchBoardListReqDto searchBoardListReqDto) { // searchBoardListReqDto 검색 데이터가 들어가 있음;

        return ResponseEntity.ok(boardService.getBoardCount(categoryName, searchBoardListReqDto));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @GetMapping("/board/like/{boardId}") // 이 게시글에 대한 like상태를 가지고 오는 것이이 Id가 필요
    public ResponseEntity<?> getLikeState(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.getLikeState(boardId));
    }

    @PostMapping("/board/like/{boardId}") // 이 게시글에 대한 like상태를 가지고 오는 것이 Id가 필요
    public ResponseEntity<?> setLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.setLike(boardId));
    }

    @DeleteMapping("/board/like/{boardId}")
    public ResponseEntity<?> cancelLike(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.cancelLike(boardId));
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable int boardId) {
        return ResponseEntity.ok(boardService.deleteBoard(boardId));
    }

    @ArgsAop
    @ValidAop
    @PutMapping("/board/{boardId}")
    public ResponseEntity<?> editBoard(@PathVariable int boardId,
                                       @Valid @RequestBody EditBoardReqDto editBoardReqDto,
                                       BindingResult bindingResult) {
        return ResponseEntity.ok(boardService.editBoard(boardId, editBoardReqDto));

    }
}

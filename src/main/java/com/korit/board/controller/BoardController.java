package com.korit.board.controller;

import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.RegisterBoardReqDto;
import com.korit.board.exception.ValidException;
import com.korit.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}

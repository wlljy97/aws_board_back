package com.korit.board.controller;

import com.korit.board.aop.annotation.ValidAop;
import com.korit.board.dto.RegisterBoardReqDto;
import com.korit.board.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BoardController {

    @ValidAop
    @PostMapping("/board/{category}") //  {category} 쓴 이유: 동적인 URL을 처리하고, 해당 카테고리에 따라 서로 다른 동작을 수행가능
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterBoardReqDto registerBoardReqDto,
            BindingResult bindingResult) {


        return ResponseEntity.ok(true);
    }
}

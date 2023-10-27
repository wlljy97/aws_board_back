package com.korit.board.controller;

import com.korit.board.dto.OrderReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    @PostMapping("/order")
    public ResponseEntity<?> order(@RequestBody OrderReqDto orderReqDto) {

        return ResponseEntity.ok(null);
    }
}

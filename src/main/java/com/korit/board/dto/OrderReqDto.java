package com.korit.board.dto;

import lombok.Data;

@Data
public class OrderReqDto {
    private int productId;
    private String email;
}

package com.korit.board.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductRespDto {
    public int productId;
    public String productName;
    private int productPrice;


}

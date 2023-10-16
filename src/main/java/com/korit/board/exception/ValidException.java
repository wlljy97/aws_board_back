package com.korit.board.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidException extends RuntimeException{
    private Map<String, String> errorMap;

    public ValidException(Map<String, String> errorMap) {
        super("유효성 검사 오류");
        this.errorMap = errorMap; // 매개변수 전달
    }
}

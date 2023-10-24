package com.korit.board.exception;

public class MismatchedPasswordException extends RuntimeException{

    public MismatchedPasswordException() { // 비밀번호 일치하지 않을 때 사용하기 위해 만듬
        super("새 비밀번호가 서로 일치하지 않습니다.");
    }
}

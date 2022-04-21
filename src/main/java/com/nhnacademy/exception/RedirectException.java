package com.nhnacademy.exception;

public class RedirectException extends RuntimeException {
    public RedirectException(){
        super("더 이상 사이트를 찾을수 없습니다.");
    }
}


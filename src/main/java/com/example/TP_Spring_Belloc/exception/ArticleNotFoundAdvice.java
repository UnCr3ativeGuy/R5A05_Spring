package com.example.TP_Spring_Belloc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ArticleNotFoundAdvice {

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String articleNotFoundHandler(ArticleNotFoundException ex) {
        return ex.getMessage();
    }
}

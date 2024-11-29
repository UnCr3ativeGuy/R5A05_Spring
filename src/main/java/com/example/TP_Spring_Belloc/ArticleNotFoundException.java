package com.example.TP_Spring_Belloc;

public class ArticleNotFoundException extends RuntimeException {
    ArticleNotFoundException(Long id) {
        super("Could not find article " + id);
    }
}

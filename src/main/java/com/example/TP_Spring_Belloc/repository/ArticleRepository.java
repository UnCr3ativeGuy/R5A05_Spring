package com.example.TP_Spring_Belloc.repository;

import com.example.TP_Spring_Belloc.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
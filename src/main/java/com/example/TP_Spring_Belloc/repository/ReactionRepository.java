package com.example.TP_Spring_Belloc.repository;

import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.Reaction;
import com.example.TP_Spring_Belloc.model.ReactionType;
import com.example.TP_Spring_Belloc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    int countByArticleAndType(Article article, ReactionType type);
    boolean existsByArticleAndUser(Article article, User user);
}
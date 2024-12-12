package com.example.TP_Spring_Belloc.repository;

import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.Reaction;
import com.example.TP_Spring_Belloc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    int countByArticleAndLiked(Article article, Boolean liked);
    boolean existsByArticleAndUser(Article article, User user);
    Optional<Reaction> findByArticleAndUser(Article article, User user);
}
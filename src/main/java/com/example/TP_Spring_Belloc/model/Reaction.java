package com.example.TP_Spring_Belloc.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 0 -> Dislike , 1-> Like
    @Column(nullable = false)
    private Boolean liked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getType() {
        return liked;
    }

    public void setType(Boolean liked) {
        this.liked = liked;
    }
}
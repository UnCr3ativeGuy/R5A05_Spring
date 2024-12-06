package com.example.TP_Spring_Belloc.controller;

import com.example.TP_Spring_Belloc.model.Reaction;
import com.example.TP_Spring_Belloc.repository.ArticleRepository;
import com.example.TP_Spring_Belloc.repository.ReactionRepository;
import com.example.TP_Spring_Belloc.repository.UserRepository;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleRepository repository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.articleService = new ArticleService(repository, userRepository, reactionRepository);
    }

    @GetMapping
    List<Article> all() {
        return articleService.findAllArticles();
    }

    @PostMapping
    Article newArticle(@RequestBody Article newArticle) {
        return articleService.createNewArticle(newArticle);
    }

    @GetMapping("/{id}")
    Article one(@PathVariable Long id) {
        return articleService.findOneArticle(id);
    }

    @PutMapping("/{id}")
    Article replaceArticle(@RequestBody Article newArticle, @PathVariable Long id) {
        return articleService.editArticle(newArticle, id);
    }

    @DeleteMapping("/{id}")
    void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @PostMapping("/{id}/user/{id_user}/like")
    Reaction like(@PathVariable Long id, @PathVariable Long id_user) {
        return articleService.likeArticle(id, id_user);
    }

    @PostMapping("/{id}/user/{id_user}/dislike")
    Reaction dislike(@PathVariable Long id, @PathVariable Long id_user) {
        return articleService.dislikeArticle(id, id_user);
    }
}

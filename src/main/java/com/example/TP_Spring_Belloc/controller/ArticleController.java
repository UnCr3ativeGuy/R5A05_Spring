package com.example.TP_Spring_Belloc.controller;

import com.example.TP_Spring_Belloc.exception.ArticleNotFoundException;
import com.example.TP_Spring_Belloc.repository.ArticleRepository;
import com.example.TP_Spring_Belloc.exception.UserNotFoundException;
import com.example.TP_Spring_Belloc.repository.UserRepository;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ArticleController {

    private final ArticleRepository repository;
    private final UserRepository userRepository;

    public ArticleController(ArticleRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/articles")
    List<Article> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/articles")
    Article newArticle(@RequestBody Article newArticle) {
        User author = userRepository.findById(newArticle.getAuthor().getId())
                .orElseThrow(() -> new UserNotFoundException(newArticle.getAuthor().getId()));
        newArticle.setAuthor(author);
        newArticle.setPublicationDate(LocalDateTime.now());
        return repository.save(newArticle);
    }

    @GetMapping("/articles/{id}")
    Article one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @PutMapping("/articles/{id}")
    Article replaceArticle(@RequestBody Article newArticle, @PathVariable Long id) {

        return repository.findById(id)
                .map(article -> {
                    article.setContent(newArticle.getContent());
                    return repository.save(article);
                })
                .orElseGet(() -> {
                    return repository.save(newArticle);
                });
    }

    @DeleteMapping("/articles/{id}")
    void deleteArticle(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

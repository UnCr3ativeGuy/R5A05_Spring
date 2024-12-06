package com.example.TP_Spring_Belloc.controller;

import com.example.TP_Spring_Belloc.exception.ArticleNotFoundException;
import com.example.TP_Spring_Belloc.model.Reaction;
import com.example.TP_Spring_Belloc.model.ReactionType;
import com.example.TP_Spring_Belloc.repository.ArticleRepository;
import com.example.TP_Spring_Belloc.exception.UserNotFoundException;
import com.example.TP_Spring_Belloc.repository.ReactionRepository;
import com.example.TP_Spring_Belloc.repository.UserRepository;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/articles")
@RestController
public class ArticleController {

    private final ArticleRepository repository;
    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;

    public ArticleController(ArticleRepository repository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.reactionRepository = reactionRepository;
    }

    @GetMapping
    List<Article> all() {
        return repository.findAll().stream()
                .map(article -> {
                    // Calculate the like and dislike counts
                    int likesCount = reactionRepository.countByArticleAndType(article, ReactionType.LIKE);
                    int dislikesCount = reactionRepository.countByArticleAndType(article, ReactionType.DISLIKE);

                    // Set the calculated counts in the article object
                    article.setLikesCount(likesCount);
                    article.setDislikesCount(dislikesCount);

                    return article; // Return the transformed article
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    Article newArticle(@RequestBody Article newArticle) {
        User author = userRepository.findById(newArticle.getAuthor().getId())
                .orElseThrow(() -> new UserNotFoundException(newArticle.getAuthor().getId()));
        newArticle.setAuthor(author);
        newArticle.setPublicationDate(LocalDateTime.now());
        return repository.save(newArticle);
    }

    @GetMapping("/{id}")
    Article one(@PathVariable Long id) {
        Article article = repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        int likesCount = reactionRepository.countByArticleAndType(article, ReactionType.LIKE);
        int dislikesCount = reactionRepository.countByArticleAndType(article, ReactionType.DISLIKE);

        article.setLikesCount(likesCount);
        article.setDislikesCount(dislikesCount);

        return article;
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    void deleteArticle(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PostMapping("/{id}/like")
    Reaction like(@PathVariable Long id, @RequestBody User user) {
        Reaction newReaction = new Reaction();
        newReaction.setArticle(repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id)));
        newReaction.setUser(userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId())));
        newReaction.setType(ReactionType.LIKE);
        return reactionRepository.save(newReaction);
    }

    @PostMapping("/{id}/dislike")
    Reaction dislike(@PathVariable Long id, @RequestBody User user) {
        Reaction newReaction = new Reaction();
        newReaction.setArticle(repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id)));
        newReaction.setUser(userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId())));
        newReaction.setType(ReactionType.DISLIKE);
        return reactionRepository.save(newReaction);
    }
}

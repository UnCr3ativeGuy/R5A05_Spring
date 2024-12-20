package com.example.TP_Spring_Belloc.service;

import com.example.TP_Spring_Belloc.exception.ArticleNotFoundException;
import com.example.TP_Spring_Belloc.exception.UserNotFoundException;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.Reaction;
import com.example.TP_Spring_Belloc.model.User;
import com.example.TP_Spring_Belloc.repository.ArticleRepository;
import com.example.TP_Spring_Belloc.repository.ReactionRepository;
import com.example.TP_Spring_Belloc.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ReactionRepository reactionRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, ReactionRepository reactionRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.reactionRepository = reactionRepository;
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAll().stream()
                .map(article -> {
                    int likesCount = reactionRepository.countByArticleAndLiked(article, true);
                    int dislikesCount = reactionRepository.countByArticleAndLiked(article, false);

                    article.setLikesCount(likesCount);
                    article.setDislikesCount(dislikesCount);

                    return article;
                })
                .collect(Collectors.toList());
    }

    public Article createNewArticle(Article article) {
        User author = userRepository.findById(article.getAuthor().getId())
                .orElseThrow(() -> new UserNotFoundException(article.getAuthor().getId()));
        article.setAuthor(author);
        article.setPublicationDate(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public Article findOneArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));

        int likesCount = reactionRepository.countByArticleAndLiked(article, true);
        int dislikesCount = reactionRepository.countByArticleAndLiked(article, false);

        article.setLikesCount(likesCount);
        article.setDislikesCount(dislikesCount);

        return article;
    }

    public Article editArticle(Article newArticle, Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setContent(newArticle.getContent());
                    return articleRepository.save(article);
                })
                .orElseGet(() -> {
                    return articleRepository.save(newArticle);
                });
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    public Reaction likeArticle(Long id, Long id_user) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        User user = userRepository.findById(id_user)
                .orElseThrow(() -> new UserNotFoundException(id_user));
        Optional<Reaction> reaction = reactionRepository.findByArticleAndUser(article, user);
        if(reaction.isPresent()) {
            Reaction oldReaction = reaction.get();
            if(oldReaction.getLiked()) {
                return null;
            } else {
                oldReaction.setLiked(true);
                return reactionRepository.save(oldReaction);
            }
        }
        Reaction newReaction = new Reaction();
        newReaction.setArticle(article);
        newReaction.setUser(user);
        newReaction.setLiked(true);
        return reactionRepository.save(newReaction);
    }

    public Reaction dislikeArticle(Long id, Long id_user) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
        User user = userRepository.findById(id_user)
                .orElseThrow(() -> new UserNotFoundException(id_user));
        Optional<Reaction> reaction = reactionRepository.findByArticleAndUser(article, user);
        if(reaction.isPresent()) {
            Reaction oldReaction = reaction.get();
            if(!oldReaction.getLiked()) {
                return null;
            } else {
                oldReaction.setLiked(false);
                return reactionRepository.save(oldReaction);
            }
        }
        Reaction newReaction = new Reaction();
        newReaction.setArticle(article);
        newReaction.setUser(user);
        newReaction.setLiked(false);
        return reactionRepository.save(newReaction);
    }
}

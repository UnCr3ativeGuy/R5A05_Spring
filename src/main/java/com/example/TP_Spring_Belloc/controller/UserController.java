package com.example.TP_Spring_Belloc.controller;

import com.example.TP_Spring_Belloc.repository.ArticleRepository;
import com.example.TP_Spring_Belloc.exception.UserNotFoundException;
import com.example.TP_Spring_Belloc.repository.UserRepository;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository repository;
    private final ArticleRepository articleRepository;

    public UserController(UserRepository repository, ArticleRepository articleRepository) {
        this.repository = repository;
        this.articleRepository = articleRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping("/users/{id}/articles")
    List<Article> allArticles(@PathVariable Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getArticles();
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setRole(newUser.getRole());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
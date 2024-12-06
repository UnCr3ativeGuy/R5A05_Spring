package com.example.TP_Spring_Belloc.controller;

import com.example.TP_Spring_Belloc.repository.UserRepository;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.User;
import com.example.TP_Spring_Belloc.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserRepository repository) {
        this.userService = new UserService(repository);
    }

    @GetMapping("/users")
    List<User> all() {
        return userService.findAllUsers();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userService.createNewUser(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return userService.findOneUser(id);
    }

    @GetMapping("/users/{id}/articles")
    List<Article> allArticles(@PathVariable Long id) {
        return userService.getUserArticles(id);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return userService.editArticle(newUser, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
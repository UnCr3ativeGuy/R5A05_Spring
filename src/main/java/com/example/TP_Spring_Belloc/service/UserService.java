package com.example.TP_Spring_Belloc.service;

import com.example.TP_Spring_Belloc.exception.UserNotFoundException;
import com.example.TP_Spring_Belloc.model.Article;
import com.example.TP_Spring_Belloc.model.User;
import com.example.TP_Spring_Belloc.repository.UserRepository;
import java.util.List;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User createNewUser(User newUser) {
        return repository.save(newUser);
    }

    public User findOneUser(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User editArticle(User newUser, Long id) {
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

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public List<Article> getUserArticles(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return user.getArticles();
    }
}

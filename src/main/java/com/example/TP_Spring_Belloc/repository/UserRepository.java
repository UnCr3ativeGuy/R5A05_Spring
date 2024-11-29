package com.example.TP_Spring_Belloc.repository;

import com.example.TP_Spring_Belloc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
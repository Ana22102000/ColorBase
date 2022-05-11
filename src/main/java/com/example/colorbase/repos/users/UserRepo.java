package com.example.colorbase.repos.users;

import com.example.colorbase.dto.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findUserByLogin(String login);

    List<User> findAllByOrderByLogin();


}

package com.example.colorbase.services.users;


import com.example.colorbase.dto.users.User;
import com.example.colorbase.repos.users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public Optional<User> getUserByLogin(String login){
        return userRepo.findUserByLogin(login);
    }

    public List<User> getAll(){
        return userRepo.findAllByOrderByLogin();
    }

//    public List<User> getUsersFromProject(Integer project_id){
//        return userRepo.getUsersFromProject(project_id);
//    }


    public User editUser(User user) {
        if (user.getPassword() == null){
            user.setPassword(userRepo.getOne(user.getId()).getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepo.saveAndFlush(user);
        return user;
    }


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.saveAndFlush(user);
        return user;
    }



}

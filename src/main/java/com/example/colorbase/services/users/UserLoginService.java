package com.example.colorbase.services.users;

import com.example.colorbase.repos.users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;


@RequiredArgsConstructor
public class UserLoginService implements UserDetailsService {

    private final UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final com.example.colorbase.dto.users.User user = repo.findUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

}

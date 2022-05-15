package com.example.colorbase.services.users;


import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.users.User;
import com.example.colorbase.repos.users.RoleRepo;
import com.example.colorbase.repos.users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepo repo;

    public Optional<Role> findRoleById(int id){
        return repo.findRoleById(id);
    }
    public Optional<Role> findRoleByRole(Role.RoleName role){
        return repo.findRoleByRole(role.toString());
    }


}

package com.example.colorbase.repos.users;

import com.example.colorbase.dto.Role;
import com.example.colorbase.dto.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleById(int id);
    Optional<Role> findRoleByRole(String role);


}

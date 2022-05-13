package com.example.colorbase.repos;

import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SetRepo extends JpaRepository<Set, Integer> {
    Optional<Set> findById(int id);
    List<Set> getAllByBrandId(int id);
    List<Set> findAll();
}

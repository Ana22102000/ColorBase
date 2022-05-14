package com.example.colorbase.repos;

import com.example.colorbase.dto.Collection;
import com.example.colorbase.dto.Colour;
import com.example.colorbase.dto.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepo extends JpaRepository<Collection, Integer> {
    Optional<Collection> findById(int id);
    List<Collection> getAllByUserId(int id);
    List<Collection> findAll();
}

package com.example.colorbase.repos;

import com.example.colorbase.dto.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ColourRepo extends JpaRepository<Colour, Integer> {
    Optional<Colour> findById(int id);
    List<Colour> getAllByBrandId(int id);

    List<Colour> getAllByApproved(boolean approved);
    List<Colour> findAll();
}

package com.example.colorbase.services;

import com.example.colorbase.dto.Colour;
import com.example.colorbase.repos.ColourRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColourService {

    private final ColourRepo repo;

    public List<Colour> getColours(){
        return repo.findAll();
    }

    public List<Colour> getColoursByBrandId(int brandId){
        return repo.getAllByBrandId(brandId);
    }


    public Optional<Colour> getById(int id){
        return repo.findById(id);
    }

    public void removeColourById(Integer id) {
        repo.deleteById(id);
    }

    public Colour addColour(Colour colour) {
        repo.saveAndFlush(colour);
        return colour;
    }

    public Colour editColour(Colour colour) {
        repo.saveAndFlush(colour);
        return colour;
    }
}